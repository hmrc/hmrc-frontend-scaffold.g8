package repositories

import java.time.LocalDateTime

import javax.inject.Inject
import models.UserAnswers
import play.api.Configuration
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.indexes.IndexType
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json.compat._

import scala.concurrent.{ExecutionContext, Future}

class DefaultSessionRepository @Inject()(
                                          mongo: ReactiveMongoApi,
                                          config: Configuration
                                        )(implicit ec: ExecutionContext) extends SessionRepository {


  private val collectionName: String = "user-answers"

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
    collection.flatMap(_.find(Json.obj("_id" -> id), None).one[UserAnswers])

  override def set(userAnswers: UserAnswers): Future[Boolean] = {

    val selector = Json.obj(
      "_id" -> userAnswers.id
    )

    val modifier = Json.obj(
      "\$set" -> (userAnswers copy (lastUpdated = LocalDateTime.now))
    )

    collection.flatMap {
      _.update(ordered = false)
        .one(selector, modifier, upsert = true).map {
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
}
