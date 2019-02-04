package views

import forms.$className$FormProvider
import models.{NormalMode, $className$}
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.OptionsViewBehaviours
import views.html.$className$View

class $className$ViewSpec extends OptionsViewBehaviours[$className$] {

  val messageKeyPrefix = "$className;format="decap"$"

  val form = new $className$FormProvider()()

  val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

  val view = application.injector.instanceOf[$className$View]

  def applyView(form: Form[_]): HtmlFormat.Appendable =
    view.apply(form, NormalMode)(fakeRequest, messages)

  "$className$View" must {

    behave like normalPage(applyView(form), messageKeyPrefix)

    behave like pageWithBackLink(applyView(form))

    behave like optionsPage(form, applyView, $className$.options)
  }
}
