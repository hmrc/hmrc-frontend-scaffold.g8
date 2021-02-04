package repositories

import models.UserAnswers
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.{BeforeAndAfterEach, OptionValues}
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.test.Helpers.running
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json.compat._

import java.time.{Clock, Instant, LocalDateTime, ZoneId}
import scala.concurrent.ExecutionContext.Implicits.global

class SessionRepositorySpec
  extends AnyFreeSpec
    with Matchers
    with MongoSuite
    with ScalaFutures
    with IntegrationPatience
    with OptionValues
    with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    database.flatMap(_.drop).futureValue
    super.beforeEach()
  }

  private val userAnswers = UserAnswers("id", Json.obj("foo" -> "bar"), LocalDateTime.of(2000, 1, 1, 1, 1, 1))

  private val instant = Instant.now
  private val stubClock: Clock = Clock.fixed(instant, ZoneId.systemDefault)

  private val appBuilder: GuiceApplicationBuilder =
    new GuiceApplicationBuilder()
      .overrides(bind[Clock].toInstance(stubClock))

  ".set" - {

    "must save the supplied answers and set the last updated time to be `now`" in {

      val app = appBuilder.build()

      running(app) {

        val repo = app.injector.instanceOf[SessionRepository]

        val expectedAnswers = userAnswers.copy(lastUpdated = LocalDateTime.ofInstant(instant, ZoneId.systemDefault.getRules.getOffset(instant)))

        val setResult = repo.set(userAnswers).futureValue
        val updatedRecord = repo.get(userAnswers.id).futureValue

        setResult mustEqual true
        updatedRecord.value mustEqual expectedAnswers
      }
    }
  }

  ".get" - {

    "when there is a record for this id" - {

      "must update the lastUpdated time to be `now` and get the record" in {

        val expectedAnswers = userAnswers.copy(lastUpdated = LocalDateTime.ofInstant(instant, ZoneId.systemDefault.getRules.getOffset(instant)))

        database.flatMap {
          _.collection[JSONCollection]("user-answers")
            .insert(ordered = false)
            .one(userAnswers)
        }.futureValue

        val app = appBuilder.build()

        running(app) {

          val repo = app.injector.instanceOf[SessionRepository]

          val result = repo.get(userAnswers.id).futureValue

          result.value mustEqual expectedAnswers
        }
      }
    }

    "when there isn't a record for this id" - {

      "must return None" in {

        val app = appBuilder.build()

        running(app) {

          val repo = app.injector.instanceOf[SessionRepository]

          val result = repo.get("some id that doesn't exist").futureValue
          result must not be defined
        }
      }
    }
  }

  ".clear" - {

    "must remove a record when it exists" in {

      val app = appBuilder.build()

      running(app) {

        val repo = app.injector.instanceOf[SessionRepository]

        repo.set(userAnswers).futureValue
        repo.get(userAnswers.id).futureValue mustBe defined

        repo.clear(userAnswers.id).futureValue
        repo.get(userAnswers.id).futureValue must not be defined
      }
    }
  }

  ".keepAlive" - {

    "when there is a record for this id" - {

      "must update its lastUpdated time to `now`" in {

        val expectedAnswers = userAnswers.copy(lastUpdated = LocalDateTime.ofInstant(instant, ZoneId.systemDefault.getRules.getOffset(instant)))

        database.flatMap {
          _.collection[JSONCollection]("user-answers")
            .insert(ordered = false)
            .one(userAnswers)
        }.futureValue

        val app = appBuilder.build()

        running(app) {

          val repo = app.injector.instanceOf[SessionRepository]

          val result = repo.keepAlive(userAnswers.id).futureValue
          result mustEqual true

          val updatedRecord = database.flatMap {
            _.collection[JSONCollection]("user-answers")
              .find(Json.obj("_id" -> userAnswers.id))
              .one[UserAnswers]
          }.futureValue

          updatedRecord.value mustEqual expectedAnswers
        }
      }
    }

    "when there isn't a record for this id" - {

      "must return true" in {

        val app = appBuilder.build()

        running(app) {

          val repo = app.injector.instanceOf[SessionRepository]

          val result = repo.keepAlive("some id that doesn't exist").futureValue
          result mustEqual true
        }
      }
    }
  }
}
