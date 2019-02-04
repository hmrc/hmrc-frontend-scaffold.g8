package views

import forms.$className$FormProvider
import models.{$className$, NormalMode}
import play.api.Application
import play.api.data.Form
import play.twirl.api.HtmlFormat
import viewmodels.RadioCheckboxOption
import views.behaviours.CheckboxViewBehaviours
import views.html.$className$View

class $className$ViewSpec extends CheckboxViewBehaviours[$className$] {

  val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

  val form = new $className$FormProvider()()

  def applyView(form: Form[Set[$className$]]): HtmlFormat.Appendable =
    application.injector.instanceOf[$className$View].apply(form, NormalMode)(fakeRequest, messages)

  val messageKeyPrefix = "$className;format="decap"$"

  val options: Set[RadioCheckboxOption] = $className$.options

  "$className$View" must {

    behave like normalPage(applyView(form), messageKeyPrefix)

    behave like pageWithBackLink(applyView(form))

    behave like checkboxPage(form, applyView, messageKeyPrefix, options)
  }

  application.stop()
}
