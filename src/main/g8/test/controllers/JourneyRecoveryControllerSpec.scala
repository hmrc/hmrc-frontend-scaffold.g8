package controllers

import base.SpecBase
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.play.bootstrap.binders.RedirectUrl
import views.html.{JourneyRecoveryContinueView, JourneyRecoveryStartAgainView}

class JourneyRecoveryControllerSpec extends SpecBase {

  "JourneyRecovery Controller" - {

    "when a relative continue Url is supplied" - {

      "must return OK and the continue view" in {

        val application = applicationBuilder(userAnswers = None).build()

        running(application) {
          val continueUrl = RedirectUrl("/foo")
          val request     = FakeRequest(GET, routes.JourneyRecoveryController.onPageLoad(Some(continueUrl)).url)

          val result = route(application, request).value

          val continueView = application.injector.instanceOf[JourneyRecoveryContinueView]

          status(result) mustEqual OK
          contentAsString(result) mustEqual continueView(continueUrl.unsafeValue)(request, messages(application)).toString
        }
      }
    }

    "when an absolute continue Url is supplied" - {

      "must return OK and the start again view" in {

        val application = applicationBuilder(userAnswers = None).build()

        running(application) {
          val continueUrl = RedirectUrl("https://foo.com")
          val request     = FakeRequest(GET, routes.JourneyRecoveryController.onPageLoad(Some(continueUrl)).url)

          val result = route(application, request).value

          val startAgainView = application.injector.instanceOf[JourneyRecoveryStartAgainView]

          status(result) mustEqual OK
          contentAsString(result) mustEqual startAgainView()(request, messages(application)).toString
        }
      }
    }

    "when no continue Url is supplied" - {

      "must return OK and the start again view" in {

        val application = applicationBuilder(userAnswers = None).build()

        running(application) {
          val request = FakeRequest(GET, routes.JourneyRecoveryController.onPageLoad().url)

          val result = route(application, request).value

          val startAgainView = application.injector.instanceOf[JourneyRecoveryStartAgainView]

          status(result) mustEqual OK
          contentAsString(result) mustEqual startAgainView()(request, messages(application)).toString
        }
      }
    }
  }
}
