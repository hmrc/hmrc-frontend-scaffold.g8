package controllers.actions

import base.SpecBase
import play.api.mvc.{BodyParsers, Results}
import play.api.test.Helpers._
import uk.gov.hmrc.http.SessionKeys

import scala.concurrent.ExecutionContext.Implicits.global

class SessionActionSpec extends SpecBase {

  class Harness(action: IdentifierAction) {
    def onPageLoad() = action { request => Results.Ok }
  }

  "Session Action" when {

    "there's no active session" must {

      "redirect to the session expired page" in {

        val application = applicationBuilder(userAnswers = None).build()

        val bodyParsers = application.injector.instanceOf[BodyParsers.Default]

        val sessionAction = new SessionIdentifierAction(frontendAppConfig, bodyParsers)

        val controller = new Harness(sessionAction)

        val result = controller.onPageLoad()(fakeRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result).get must startWith(controllers.routes.SessionExpiredController.onPageLoad().url)
      }
    }

    "there's an active session" must {

      "perform the action" in {

        val application = applicationBuilder(userAnswers = None).build()

        val bodyParsers = application.injector.instanceOf[BodyParsers.Default]

        val sessionAction = new SessionIdentifierAction(frontendAppConfig, bodyParsers)

        val controller = new Harness(sessionAction)

        val request = fakeRequest.withSession(SessionKeys.sessionId -> "foo")

        val result = controller.onPageLoad()(request)

        status(result) mustBe OK
      }
    }
  }
}
