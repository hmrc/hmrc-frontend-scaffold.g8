package controllers.auth

import base.SpecBase
import config.FrontendAppConfig
import org.mockito.ArgumentMatchers.{any, eq => eqTo}
import org.mockito.Mockito.{times, verify, when}
import org.scalatestplus.mockito.MockitoSugar
import play.api.test.FakeRequest
import play.api.test.Helpers._
import repositories.SessionRepository

import java.net.URLEncoder

import scala.concurrent.Future

class AuthControllerSpec extends SpecBase with MockitoSugar {
  private class Setup extends MockitoSugar {
    val appConfig: FrontendAppConfig = mock
    when(appConfig.signOutUrl) thenReturn "/sign-out"
    when(appConfig.exitSurveyUrl) thenReturn "/sign-out/exit-survey"

    val mockSessionRepository: SessionRepository = mock
    when(mockSessionRepository.clear(any())) thenReturn Future.successful(true)

    val controllerUnderTest = new AuthController(
      mockAppControllerComponents(),
      appConfig,
      mockSessionRepository
    )
  }

  "signOut" - {

    "must clear user answers and redirect to sign out, specifying the exit survey as the continue URL" in new Setup {
      private val result = controllerUnderTest.signOut()(FakeRequest())

      private val encodedContinueUrl = URLEncoder.encode(appConfig.exitSurveyUrl, "UTF-8")
      private val expectedRedirectUrl = s"\${appConfig.signOutUrl}?continue=\$encodedContinueUrl"

      status(result) mustEqual SEE_OTHER
      redirectLocation(result).value mustEqual expectedRedirectUrl
      verify(mockSessionRepository, times(1)).clear(eqTo(userAnswersId))
    }
  }

  "signOutNoSurvey" - {

    "must clear users answers and redirect to sign out, specifying SignedOut as the continue URL" in new Setup {
      private val result = controllerUnderTest.signOutNoSurvey()(FakeRequest())

      private val encodedContinueUrl = URLEncoder.encode(routes.SignedOutController.onPageLoad().url, "UTF-8")
      private val expectedRedirectUrl = s"\${appConfig.signOutUrl}?continue=\$encodedContinueUrl"

      status(result) mustEqual SEE_OTHER
      redirectLocation(result).value mustEqual expectedRedirectUrl
      verify(mockSessionRepository, times(1)).clear(eqTo(userAnswersId))
    }
  }
}
