package controllers.auth

import base.SpecBase
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{verify, when}
import org.scalatestplus.mockito.MockitoSugar
import play.api.test.FakeRequest
import play.api.test.Helpers._
import views.html.auth.SignedOutView

class SignedOutControllerSpec extends SpecBase {
  private class Setup extends MockitoSugar {
    val mockView: SignedOutView = mock
    when(mockView.apply()(any, any)) thenReturn play.twirl.api.Html("SignedOut")

    val controllerUnderTest = new SignedOutController(baseControllerComponents, mockView)
  }

  "SignedOut Controller" - {

    "must return OK and the correct view for a GET" in new Setup {
      private val result = controllerUnderTest.onPageLoad()(FakeRequest())

      status(result) mustEqual OK
      verify(mockView).apply()(any, any)
    }
  }
}
