package views

import views.behaviours.ViewBehaviours
import views.html.index

class IndexViewSpec extends ViewBehaviours {

  def view = () => index(frontendAppConfig)(fakeRequest, messages)

  "Index view" must {

    behave like normalPage(view, "index", "guidance")
  }
}
