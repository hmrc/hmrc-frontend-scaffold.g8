package views

import play.api.data.Form
import controllers.routes
import forms.$className$Form
import models.{NormalMode, $className$}
import views.behaviours.QuestionViewBehaviours
import views.html.$className;format="decap"$

class $className$ViewSpec extends QuestionViewBehaviours[$className$] {

  val messageKeyPrefix = "$className;format="decap"$"

  def createView = () => $className;format="decap"$(frontendAppConfig, $className$Form(), NormalMode)(fakeRequest, messages)

  def createViewUsingForm = (form: Form[_]) => $className;format="decap"$(frontendAppConfig, form, NormalMode)(fakeRequest, messages)

  override val form = $className$Form()

  "$className$ view" must {

    behave like normalPage(createView, messageKeyPrefix)

    behave like pageWithTextFields(createViewUsingForm, messageKeyPrefix, routes.$className$Controller.onSubmit(NormalMode).url, "field1", "field2")
  }
}
