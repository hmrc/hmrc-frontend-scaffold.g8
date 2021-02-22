package repositories

import models.{MongoDateTimeFormats, UserAnswers}
import play.api.Configuration
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.indexes.IndexType
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json.compat._

import java.time.{Clock, LocalDateTime}
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class DefaultSessionRepository @Inject()(
                                          mongo: ReactiveMongoApi,
                                          config: Configuration,
                                          clock: Clock
                                        )(implicit ec: ExecutionContext) extends SessionRepository {


  private val collectionName: String = "user-answers"

  private def byId(id: String): JsObject = Json.obj("_id" -> id)

  private val cacheTtl = config.get[Int]("mongodb.timeToLiveInSeconds")

  private def collection: Future[JSONCollection] =
    mongo.database.map(_.collection[JSONCollection](collectionName))

  private val lastUpdatedIndex = IndexProvider.index(
    key                = Seq("lastUpdated" -> IndexType.Ascending),
    name               = Some("user-answers-last-updated-index"),
    expireAfterSeconds = Some(cacheTtl)
  )

  val started: Future[Boolean] =
    collection.flatMap {
      _.indexesManager.ensure(lastUpdatedIndex)
    }.map(_ => true)

  override def get(id: String): Future[Option[UserAnswers]] =
    keepAlive(id).flatMap {
      _ =>
        collection.flatMap {
          _.find(byId(id), projection = None)
            .one[UserAnswers]
        }
    }

  override def set(userAnswers: UserAnswers): Future[Boolean] = {

    val modifier = Json.obj(
      "\$set" -> (userAnswers copy (lastUpdated = LocalDateTime.now(clock)))
    )

    collection.flatMap {
      _.update(ordered = false)
        .one(byId(userAnswers.id), modifier, upsert = true).map {
          lastError =>
            lastError.ok
      }
    }
  }

  override def clear(id: String): Future[Boolean] =
    collection.flatMap(_.delete.one(byId(id)).map(_.ok))

  override def keepAlive(id: String): Future[Boolean] = {

    implicit val dateWrites: Writes[LocalDateTime] = MongoDateTimeFormats.localDateTimeWrite

    val modifier = Json.obj("\$set" -> Json.obj("lastUpdated" -> LocalDateTime.now(clock)))

    collection.flatMap {
      _.update(ordered = false)
        .one(byId(id), modifier)
        .map {
          lastError =>
            lastError.ok
        }
    }
  }
}

trait SessionRepository {

  val started: Future[Boolean]

  def get(id: String): Future[Option[UserAnswers]]

  def set(userAnswers: UserAnswers): Future[Boolean]

  def clear(id: String): Future[Boolean]

  def keepAlive(id: String): Future[Boolean]
}
