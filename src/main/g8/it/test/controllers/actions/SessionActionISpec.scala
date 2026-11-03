package controllers.actions

import base.ISpecBase
import play.api.mvc.{Action, AnyContent, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.SessionKeys

class SessionActionISpec extends ISpecBase {
  private lazy val action = app.injector.instanceOf[SessionIdentifierAction]

  object Harness {
    def onPageLoad(): Action[AnyContent] = action { _ => Results.Ok }
  }

  "Session Action" - {

    "when there is no active session" - {

      "must redirect to the session expired page" in {
        val result = Harness.onPageLoad()(FakeRequest())

        status(result) mustBe SEE_OTHER
        redirectLocation(result).value must startWith(controllers.routes.JourneyRecoveryController.onPageLoad().url)
      }
    }

    "when there is an active session" - {

      "must perform the action" in {
        val request = FakeRequest().withSession(SessionKeys.sessionId -> "foo")

        val result = Harness.onPageLoad()(request)
        status(result) mustBe OK
      }
    }
  }
}
