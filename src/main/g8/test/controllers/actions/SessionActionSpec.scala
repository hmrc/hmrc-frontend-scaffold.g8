package controllers.actions

import base.SpecBase
import config.FrontendAppConfig
import play.api.mvc.{BodyParsers, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.SessionKeys

import scala.concurrent.ExecutionContext.Implicits.global

class SessionActionSpec extends SpecBase {

  class Harness(action: IdentifierAction) {
    def onPageLoad() = action { request => Results.Ok }
  }

  "Session Action" - {

    "when there is no active session" - {

      "must redirect to the session expired page" in {

        val application = applicationBuilder(userAnswers = None).build()

        running(application){
          val bodyParsers = application.injector.instanceOf[BodyParsers.Default]
          val appConfig   = application.injector.instanceOf[FrontendAppConfig]

          val sessionAction = new SessionIdentifierAction(appConfig, bodyParsers)

          val controller = new Harness(sessionAction)

          val result = controller.onPageLoad()(FakeRequest())

          status(result) mustBe SEE_OTHER
          redirectLocation(result).value must startWith(controllers.routes.SessionExpiredController.onPageLoad().url)
        }
      }
    }

    "when there is an active session" - {

      "must perform the action" in {

        val application = applicationBuilder(userAnswers = None).build()

        running(application) {
          val bodyParsers = application.injector.instanceOf[BodyParsers.Default]
          val appConfig   = application.injector.instanceOf[FrontendAppConfig]

          val sessionAction = new SessionIdentifierAction(appConfig, bodyParsers)

          val controller = new Harness(sessionAction)

          val request = FakeRequest().withSession(SessionKeys.sessionId -> "foo")

          val result = controller.onPageLoad()(request)
          status(result) mustBe OK
        }
      }
    }
  }
}
