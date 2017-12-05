package forms

import javax.inject.Inject

import forms.mappings.Mappings
import play.api.data.Form

class $className$FormProvider @Inject() extends FormErrorHelper with Mappings {

  def apply(): Form[Int] =
    Form(
      "value" -> int(
        "$className;format="decap"$.error.required",
        "$className;format="decap"$.error.wholeNumber",
        "$className;format="decap"$.error.nonNumeric")
          .verifying(minimumValue(0, "$className;format="decap"$.error.minimum"))
    )
}
