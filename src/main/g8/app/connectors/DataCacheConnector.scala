package connectors

import com.google.inject.{ImplementedBy, Inject}
import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.http.cache.client.CacheMap
import repositories.SessionRepository
import utils.CascadeUpsert
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class DataCacheConnectorImpl @Inject()(val sessionRepository: SessionRepository, val cascadeUpsert: CascadeUpsert) extends DataCacheConnector {

  def save[A](cacheId: String, key: String, value: A)(implicit fmt: Format[A]): Future[CacheMap] = {
    sessionRepository().get(cacheId).flatMap { optionalCacheMap =>
      val updatedCacheMap = cascadeUpsert(key, value, optionalCacheMap.getOrElse(new CacheMap(cacheId, Map())))
      sessionRepository().upsert(updatedCacheMap).map {_ => updatedCacheMap}
    }
  }

  def remove(cacheId: String, key: String): Future[Boolean] = {
    sessionRepository().get(cacheId).flatMap { optionalCacheMap =>
      optionalCacheMap.fold(Future(false)) { cacheMap =>
        val newCacheMap = cacheMap copy (data = cacheMap.data - key)
        sessionRepository().upsert(newCacheMap)
      }
    }
  }

  def fetch(cacheId: String): Future[Option[CacheMap]] =
    sessionRepository().get(cacheId)

  def getEntry[A](cacheId: String, key: String)(implicit fmt: Format[A]): Future[Option[A]] = {
    fetch(cacheId).map { optionalCacheMap =>
      optionalCacheMap.flatMap { cacheMap => cacheMap.getEntry(key)}
    }
  }

  def addToCollection[A](cacheId: String, collectionKey: String, value: A)(implicit fmt: Format[A]): Future[CacheMap] = {
    sessionRepository().get(cacheId).flatMap { optionalCacheMap =>
      val updatedCacheMap = cascadeUpsert.addRepeatedValue(collectionKey, value, optionalCacheMap.getOrElse(new CacheMap(cacheId, Map())))
      sessionRepository().upsert(updatedCacheMap).map {_ => updatedCacheMap}
    }
  }

  def removeFromCollection[A](cacheId: String, collectionKey: String, item: A)(implicit fmt: Format[A]): Future[CacheMap] = {
    sessionRepository().get(cacheId).flatMap { optionalCacheMap =>
      optionalCacheMap.fold(throw new Exception(s"Couldn't find document with key \$cacheId")) {cacheMap =>
        val newSeq = cacheMap.data(collectionKey).as[Seq[A]].filterNot(x => x == item)
        val newCacheMap = if (newSeq.isEmpty) {
          cacheMap copy (data = cacheMap.data - collectionKey)
        } else {
          cacheMap copy (data = cacheMap.data + (collectionKey -> Json.toJson(newSeq)))
        }

        sessionRepository().upsert(newCacheMap).map {_ => newCacheMap}
      }
    }
  }

  def replaceInCollection[A](cacheId: String, collectionKey: String, index: Int, item: A)(implicit fmt: Format[A]): Future[CacheMap] = {
    sessionRepository().get(cacheId).flatMap { optionalCacheMap =>
      optionalCacheMap.fold(throw new Exception(s"Couldn't find document with key \$cacheId")) {cacheMap =>
        val newSeq = cacheMap.data(collectionKey).as[Seq[A]].updated(index, item)
        val updatedCacheMap = cacheMap copy (data = cacheMap.data + (collectionKey -> Json.toJson(newSeq)))
        sessionRepository().upsert(updatedCacheMap).map {_ => updatedCacheMap}
      }
    }
  }
}

@ImplementedBy(classOf[DataCacheConnectorImpl])
trait DataCacheConnector {
  def save[A](cacheId: String, key: String, value: A)(implicit fmt: Format[A]): Future[CacheMap]

  def remove(cacheId: String, key: String): Future[Boolean]

  def fetch(cacheId: String): Future[Option[CacheMap]]

  def getEntry[A](cacheId: String, key: String)(implicit fmt: Format[A]): Future[Option[A]]

  def addToCollection[A](cacheId: String, collectionKey: String, value: A)(implicit fmt: Format[A]): Future[CacheMap]

  def removeFromCollection[A](cacheId: String, collectionKey: String, item: A)(implicit fmt: Format[A]): Future[CacheMap]

  def replaceInCollection[A](cacheId: String, collectionKey: String, index: Int, item: A)(implicit fmt: Format[A]): Future[CacheMap]
}
