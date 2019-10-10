package controllers

import base.SpecBase
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.any
import org.mockito.Mockito.{times, verify, when}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.twirl.api.Html

import scala.concurrent.Future

class UnauthorisedControllerSpec extends SpecBase {

  "Unauthorised Controller" must {

    "return OK and the correct view for a GET" in {

      when(mockRenderer.render(any())(any()))
        .thenReturn(Future.successful(Html("")))

      val application = applicationBuilder(userAnswers = None).build()

      val request = FakeRequest(GET, routes.UnauthorisedController.onPageLoad().url)

      val result = route(application, request).value

      status(result) mustEqual OK

      val templateCaptor = ArgumentCaptor.forClass(classOf[String])

      verify(mockRenderer, times(1)).render(templateCaptor.capture())(any())

      templateCaptor.getValue mustEqual "unauthorised.njk"

      application.stop()
    }
  }
}
