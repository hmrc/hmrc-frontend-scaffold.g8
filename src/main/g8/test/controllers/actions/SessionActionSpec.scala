package controllers.actions

import base.SpecBase
import play.api.mvc.{Action, AnyContent, BodyParsers, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.SessionKeys

import scala.concurrent.ExecutionContext.Implicits.global

class SessionActionSpec extends SpecBase {

  class Harness(action: IdentifierAction) {
    def onPageLoad(): Action[AnyContent] = action { _ => Results.Ok }
  }

  "Session Action" - {

    "when there is no active session" - {

      "must redirect to the session expired page" in {

        val application = applicationBuilder(userAnswers = None).build()

        running(application){
          val bodyParsers = application.injector.instanceOf[BodyParsers.Default]

          val sessionAction = new SessionIdentifierAction(bodyParsers)

          val controller = new Harness(sessionAction)

          val result = controller.onPageLoad()(FakeRequest())

          status(result) mustBe SEE_OTHER
          redirectLocation(result).value must startWith(controllers.routes.JourneyRecoveryController.onPageLoad().url)
        }
      }
    }

    "when there is an active session" - {

      "must perform the action" in {

        val application = applicationBuilder(userAnswers = None).build()

        running(application) {
          val bodyParsers = application.injector.instanceOf[BodyParsers.Default]

          val sessionAction = new SessionIdentifierAction(bodyParsers)

          val controller = new Harness(sessionAction)

          val request = FakeRequest().withSession(SessionKeys.sessionId -> "foo")

          val result = controller.onPageLoad()(request)
          status(result) mustBe OK
        }
      }
    }
  }
}
