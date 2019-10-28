package views

import controllers.routes
import forms.$className$FormProvider
import models.{NormalMode, $className$}
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.QuestionViewBehaviours
import views.html.$className$View

class $className$ViewSpec extends QuestionViewBehaviours[$className$] {

  val messageKeyPrefix = "$className;format="decap"$"

  override val form = new $className$FormProvider()()

  "$className$View" must {

    val view = viewFor[$className$View](Some(emptyUserAnswers))

    def applyView(form: Form[_]): HtmlFormat.Appendable =
      view.apply(form, NormalMode)(fakeRequest, messages)


    behave like normalPage(applyView(form), messageKeyPrefix)

    behave like pageWithBackLink(applyView(form))

    behave like pageWithTextFields(
      form,
      applyView,
      messageKeyPrefix,
      routes.$className$Controller.onSubmit(NormalMode).url,
      "$field1Name$", "$field2Name$"
    )
  }
}
