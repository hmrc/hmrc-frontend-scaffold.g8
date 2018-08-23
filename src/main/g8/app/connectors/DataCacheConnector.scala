package connectors

import com.google.inject.{ImplementedBy, Inject}
import play.api.libs.json.Format
import uk.gov.hmrc.http.cache.client.CacheMap
import repositories.SessionRepository
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class MongoCacheConnector @Inject()(val sessionRepository: SessionRepository) extends DataCacheConnector {

  def save[A](cacheMap: CacheMap): Future[CacheMap] = {
    sessionRepository().upsert(cacheMap).map{_ => cacheMap}
  }
  
  def fetch(cacheId: String): Future[Option[CacheMap]] =
    sessionRepository().get(cacheId)

  def getEntry[A](cacheId: String, key: String)(implicit fmt: Format[A]): Future[Option[A]] = {
    fetch(cacheId).map { optionalCacheMap =>
      optionalCacheMap.flatMap { cacheMap => cacheMap.getEntry(key)}
    }
  }
}

trait DataCacheConnector {
  def save[A](cacheMap: CacheMap): Future[CacheMap]

  def fetch(cacheId: String): Future[Option[CacheMap]]

  def getEntry[A](cacheId: String, key: String)(implicit fmt: Format[A]): Future[Option[A]]
}
