package controllers

import base.SpecBase
import models.UserAnswers
import org.mockito.ArgumentMatchers.{any, eq as eqTo}
import org.mockito.Mockito.{verify, when}
import org.scalatestplus.mockito.MockitoSugar
import play.api.test.FakeRequest
import play.api.test.Helpers._
import viewmodels.govuk.SummaryListFluency
import views.html.CheckYourAnswersView

class CheckYourAnswersControllerSpec extends SpecBase with SummaryListFluency {
  private abstract class Setup extends MockitoSugar {
    val givenUserAnswers: Option[UserAnswers]

    val mockView: CheckYourAnswersView = mock
    when(mockView.apply(any)(any, any)) thenReturn play.twirl.api.Html("CheckYourAnswers")

    lazy val controllerUnderTest = new CheckYourAnswersController(
      mockAppControllerComponents(givenUserAnswers),
      mockView
    )
  }

  "Check Your Answers Controller" - {

    "must return OK and the correct view for a GET" in new Setup {
      val givenUserAnswers: Option[UserAnswers] = Some(emptyUserAnswers)

      private val result = controllerUnderTest.onPageLoad()(FakeRequest())

      private val expectedList = SummaryListViewModel(Seq.empty)

      status(result) mustEqual OK
      verify(mockView).apply(eqTo(expectedList))(any, any)
    }

    "must redirect to Journey Recovery for a GET if no existing data is found" in new Setup {
      val givenUserAnswers: Option[UserAnswers] = None

      private val result = controllerUnderTest.onPageLoad()(FakeRequest())

      status(result) mustEqual SEE_OTHER
      redirectLocation(result).value mustEqual routes.JourneyRecoveryController.onPageLoad().url
    }
  }
}
