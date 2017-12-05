package views

import play.api.data.Form
import controllers.routes
import forms.$className$FormProvider
import models.NormalMode
import views.behaviours.IntViewBehaviours
import views.html.$className;format="decap"$

class $className$ViewSpec extends IntViewBehaviours {

  val messageKeyPrefix = "$className;format="decap"$"

  val form = new $className$FormProvider()()

  def createView = () => $className;format="decap"$(frontendAppConfig, form, NormalMode)(fakeRequest, messages)

  def createViewUsingForm = (form: Form[_]) => $className;format="decap"$(frontendAppConfig, form, NormalMode)(fakeRequest, messages)

  "$className$ view" must {
    behave like normalPage(createView, messageKeyPrefix)

    behave like intPage(createViewUsingForm, messageKeyPrefix, routes.$className$Controller.onSubmit(NormalMode).url)
  }
}
