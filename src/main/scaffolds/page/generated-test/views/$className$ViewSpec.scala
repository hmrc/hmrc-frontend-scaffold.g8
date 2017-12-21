package views

import views.behaviours.ViewBehaviours
import views.html.$className;format="decap"$

class $className$ViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "$className;format="decap"$"

  def createView = () => $className;format="decap"$(frontendAppConfig)(fakeRequest, messages)

  "$className$ view" must {
    behave like normalPage(createView, messageKeyPrefix)
  }
}
