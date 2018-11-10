package views

import views.behaviours.ViewBehaviours
import views.html.$className$View

class $className$ViewSpec extends ViewBehaviours {

  "$className$ view" must {

    val application = applicationBuilder(userData = Some(emptyUserData)).build()

    val view = application.injector.instanceOf[$className$View]

    val applyView = view.apply()(fakeRequest, messages)

    behave like normalPage(applyView, "$className;format="decap"$")

    behave like pageWithBackLink(applyView)
  }
}
