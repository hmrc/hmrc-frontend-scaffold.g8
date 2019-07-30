package views

import forms.$className$FormProvider
import models.{$className$, NormalMode}
import play.api.Application
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.CheckboxViewBehaviours
import views.html.$className$View

class $className$ViewSpec extends CheckboxViewBehaviours[$className$] {

  val messageKeyPrefix = "$className;format="decap"$"

  val form = new $className$FormProvider()()

  "$className$View" must {

    val view = viewFor[$className$View](Some(emptyUserAnswers))

    def applyView(form: Form[Set[$className$]]): HtmlFormat.Appendable =
      view.apply(form, NormalMode)(fakeRequest, messages)

    behave like normalPage(applyView(form), messageKeyPrefix)

    behave like pageWithBackLink(applyView(form))

    behave like checkboxPage(form, applyView, messageKeyPrefix, $className$.options)
  }
}
