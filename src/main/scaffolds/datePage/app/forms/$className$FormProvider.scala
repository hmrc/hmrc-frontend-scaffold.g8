package forms

import forms.mappings.Mappings
import play.api.data.Form
import play.api.i18n.Messages

import java.time.LocalDate
import javax.inject.Inject

class $className$FormProvider @Inject() extends Mappings {

  def apply()(implicit messages: Messages): Form[LocalDate] =
    Form(
      "value" -> localDate(
        invalidKey     = "$className;format="decap"$.error.invalid",
        allRequiredKey = "$className;format="decap"$.error.required.all",
        twoRequiredKey = "$className;format="decap"$.error.required.two",
        requiredKey    = "$className;format="decap"$.error.required"
      )
    )
}
