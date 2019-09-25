package forms

import forms.mappings.Mappings
import javax.inject.Inject
import play.api.data.Form

class $className$FormProvider @Inject() extends Mappings {

  def apply(): Form[Int] =
    Form(
      "value" -> int(
        "$className;format="decap"$.error.required",
        "$className;format="decap"$.error.wholeNumber",
        "$className;format="decap"$.error.nonNumeric")
          .verifying(inRange($minimum$, $maximum$, "$className;format="decap"$.error.outOfRange"))
    )
}
