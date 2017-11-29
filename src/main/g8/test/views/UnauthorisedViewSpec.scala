package views

import views.behaviours.ViewBehaviours
import views.html.unauthorised

class UnauthorisedViewSpec extends ViewBehaviours {

  def view = () => unauthorised(frontendAppConfig)(fakeRequest, messages)

  "Unauthorised view" must {

    behave like normalPage(view, "unauthorised")
  }
}
