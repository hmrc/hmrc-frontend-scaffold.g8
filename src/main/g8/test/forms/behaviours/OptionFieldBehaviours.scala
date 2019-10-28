package forms.behaviours

import play.api.data.{Form, FormError}

class OptionFieldBehaviours extends FieldBehaviours {

  def optionsField[T](form: Form[_],
                      fieldName: String,
                      validValues: Seq[T],
                      invalidError: FormError): Unit = {


    "bind all valid values" in {

      for(value <- validValues) {

        val result = form.bind(Map(fieldName -> value.toString)).apply(fieldName)
        result.value.value shouldEqual value.toString
      }
    }

    "not bind invalid values" in {

      val generator = stringsExceptSpecificValues(validValues.map(_.toString))

      forAll(generator -> "invalidValue") {
        value =>

          val result = form.bind(Map(fieldName -> value)).apply(fieldName)
          result.errors shouldEqual Seq(invalidError)
      }
    }
  }
}
