package forms.behaviours

import play.api.data.{Form, FormError}

trait StringFieldBehaviours extends FieldBehaviours {

    def fieldWithMaxLength(form: Form[_],
                           fieldName: String,
                           maxLength: Int,
                           lengthError: FormError): Unit = {

    s"not bind strings longer than \$maxLength characters" in {

      forAll(stringsLongerThan(maxLength) -> "longString") {
        (string: String) =>
          val result = form.bind(Map(fieldName -> string)).apply(fieldName)
          result.errors must contain only lengthError
      }
    }
  }
}
