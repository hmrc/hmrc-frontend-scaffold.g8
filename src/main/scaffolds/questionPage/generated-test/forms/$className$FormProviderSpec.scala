package forms

import forms.behaviours.StringFieldBehaviours
import play.api.data.FormError

class $className$FormProviderSpec extends StringFieldBehaviours {

  val form = new $className$FormProvider()()

  ".$field1Name$" - {

    val fieldName = "$field1Name$"
    val requiredKey = "$className;format="decap"$.error.$field1Name$.required"
    val lengthKey = "$className;format="decap"$.error.$field1Name$.length"
    val maxLength = $field1MaxLength$

    behave like fieldThatBindsValidData(
      form,
      fieldName,
      stringsWithMaxLength(maxLength)
    )

    behave like fieldWithMaxLength(
      form,
      fieldName,
      maxLength = maxLength,
      lengthError = FormError(fieldName, lengthKey, Seq(maxLength))
    )

    behave like mandatoryField(
      form,
      fieldName,
      requiredError = FormError(fieldName, requiredKey)
    )
  }

  ".$field2Name$" - {

    val fieldName = "$field2Name$"
    val requiredKey = "$className;format="decap"$.error.$field2Name$.required"
    val lengthKey = "$className;format="decap"$.error.$field2Name$.length"
    val maxLength = $field2MaxLength$

    behave like fieldThatBindsValidData(
      form,
      fieldName,
      stringsWithMaxLength(maxLength)
    )

    behave like fieldWithMaxLength(
      form,
      fieldName,
      maxLength = maxLength,
      lengthError = FormError(fieldName, lengthKey, Seq(maxLength))
    )

    behave like mandatoryField(
      form,
      fieldName,
      requiredError = FormError(fieldName, requiredKey)
    )
  }
}
