package forms.behaviours

import play.api.data.{Form, FormError}

trait BooleanFieldBehaviours extends FieldBehaviours {

  def booleanField(form: Form[_],
                   fieldName: String,
                   invalidError: FormError): Unit = {

    "bind true" in {
      val result = form.bind(Map(fieldName -> "true"))
      result.value.value shouldBe true
    }

    "bind false" in {
      val result = form.bind(Map(fieldName -> "false"))
      result.value.value shouldBe false
    }

    "not bind non-booleans" in {

      forAll(nonBooleans -> "nonBoolean") {
        nonBoolean =>
          val result = form.bind(Map(fieldName -> nonBoolean)).apply(fieldName)
          result.errors shouldEqual Seq(invalidError)
      }
    }
  }
}
