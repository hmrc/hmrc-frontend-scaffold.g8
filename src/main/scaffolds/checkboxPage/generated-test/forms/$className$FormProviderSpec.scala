package forms

import forms.behaviours.CheckboxFieldBehaviours
import models.$className$
import play.api.data.FormError

class $className$FormProviderSpec extends CheckboxFieldBehaviours {

  val form = new $className$FormProvider()()

  ".value" - {

    val fieldName = "value"
    val requiredKey = "$className;format="decap"$.error.required"

    behave like checkboxField[$className$](
      form,
      fieldName,
      validValues  = $className$.values,
      invalidError = FormError(s"\$fieldName[0]", "error.invalid")
    )

    behave like mandatoryCheckboxField(
      form,
      fieldName,
      requiredKey
    )
  }
}
