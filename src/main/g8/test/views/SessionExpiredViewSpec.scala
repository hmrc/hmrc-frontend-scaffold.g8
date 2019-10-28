package views

import views.behaviours.ViewBehaviours
import views.html.SessionExpiredView

class SessionExpiredViewSpec extends ViewBehaviours {

  "Session Expired view" must {

    val application = applicationBuilder().build()

    val view = application.injector.instanceOf[SessionExpiredView]

    val applyView = view.apply()(fakeRequest, messages)

    behave like normalPage(applyView, "session_expired", "guidance")
  }
}
