package views

import views.behaviours.ViewBehaviours
import views.html.IndexView

class IndexViewSpec extends ViewBehaviours {

  "Index view" must {

    val application = applicationBuilder().build()

    val view = application.injector.instanceOf[IndexView]

    val applyView = view.apply()(fakeRequest, messages)

    behave like normalPage(applyView, "index", "guidance")
  }
}
