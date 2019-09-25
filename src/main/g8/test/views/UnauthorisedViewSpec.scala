package views

import views.behaviours.ViewBehaviours
import views.html.UnauthorisedView

class UnauthorisedViewSpec extends ViewBehaviours {

  "Unauthorised view" must {

    val application = applicationBuilder().build()

    val view = application.injector.instanceOf[UnauthorisedView]

    val applyView = view.apply()(fakeRequest, messages)

    behave like normalPage(applyView, "unauthorised")
  }
}
