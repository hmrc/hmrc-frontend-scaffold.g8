package controllers

import play.api.test.Helpers._
import views.html.unauthorised

class UnauthorisedControllerSpec extends ControllerSpecBase {

  "Unauthorised Controller" must {
    "return 200 for a GET" in {
      val result = new UnauthorisedController(frontendAppConfig, messagesApi).onPageLoad()(fakeRequest)
      status(result) mustBe OK
    }

    "return the correct view for a GET" in {
      val result = new UnauthorisedController(frontendAppConfig, messagesApi).onPageLoad()(fakeRequest)
      contentAsString(result) mustBe unauthorised(frontendAppConfig)(fakeRequest, messages).toString
    }
  }
}
