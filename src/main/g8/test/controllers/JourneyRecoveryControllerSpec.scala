package controllers

import base.SpecBase
import org.mockito.ArgumentMatchers.{any, eq as eqTo}
import org.mockito.Mockito.{verify, when}
import org.scalatestplus.mockito.MockitoSugar
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.play.bootstrap.binders.RedirectUrl
import views.html.{JourneyRecoveryContinueView, JourneyRecoveryStartAgainView}

class JourneyRecoveryControllerSpec extends SpecBase {
  private abstract class Setup extends MockitoSugar {
    val mockContinueView: JourneyRecoveryContinueView = mock
    when(mockContinueView.apply(any)(any, any)) thenReturn play.twirl.api.Html("JourneyRecoveryContinue")

    val mockStartAgainView: JourneyRecoveryStartAgainView = mock
    when(mockStartAgainView.apply()(any, any)) thenReturn play.twirl.api.Html("JourneyRecoveryStartAgain")

    lazy val controllerUnderTest = new JourneyRecoveryController(
      mockAppControllerComponents(),
      mockContinueView,
      mockStartAgainView
    )
  }

  "JourneyRecovery Controller" - {

    "when a relative continue Url is supplied" - {

      "must return OK and the continue view" in new Setup {
        val continueUrl = RedirectUrl("/foo")

        private val result = controllerUnderTest.onPageLoad(Some(continueUrl))(FakeRequest())

        status(result) mustEqual OK
        verify(mockContinueView).apply(eqTo(continueUrl.unsafeValue))(any, any)
      }
    }

    "when an absolute continue Url is supplied" - {

      "must return OK and the start again view" in new Setup {
        val continueUrl = RedirectUrl("https://foo.com")

        private val result = controllerUnderTest.onPageLoad(Some(continueUrl))(FakeRequest())

        status(result) mustEqual OK
        verify(mockStartAgainView).apply()(any, any)
      }
    }

    "when no continue Url is supplied" - {

      "must return OK and the start again view" in new Setup {
        private val result = controllerUnderTest.onPageLoad(None)(FakeRequest())

        status(result) mustEqual OK
        verify(mockStartAgainView).apply()(any, any)
      }
    }
  }
}
