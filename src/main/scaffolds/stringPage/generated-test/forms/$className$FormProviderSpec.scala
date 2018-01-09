package forms

import forms.behaviours.FieldBehaviours
import play.api.data.FormError

class $className$FormProviderSpec extends FieldBehaviours {

  val requiredKey = "$className;format="decap"$.error.required"

  val form = new $className$FormProvider()()

  ".value" must {

    val fieldName = "value"

    behave like fieldThatBindsValidData(
      form,
      fieldName,
      nonEmptyString
    )

    behave like mandatoryField(
      form,
      fieldName,
      requiredError = FormError(fieldName, requiredKey)
    )
  }
}
