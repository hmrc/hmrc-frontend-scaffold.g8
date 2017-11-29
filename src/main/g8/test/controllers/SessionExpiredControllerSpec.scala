package controllers

import play.api.test.Helpers._
import views.html.session_expired

class SessionExpiredControllerSpec extends ControllerSpecBase {

  "SessionExpired Controller" must {
    "return 200 for a GET" in {
      val result = new SessionExpiredController(frontendAppConfig, messagesApi).onPageLoad()(fakeRequest)
      status(result) mustBe OK
    }

    "return the correct view for a GET" in {
      val result = new SessionExpiredController(frontendAppConfig, messagesApi).onPageLoad()(fakeRequest)
      contentAsString(result) mustBe session_expired(frontendAppConfig)(fakeRequest, messages).toString
    }
  }
}
