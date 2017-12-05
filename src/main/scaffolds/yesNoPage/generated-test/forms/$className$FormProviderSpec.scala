package forms

class $className$FormProviderSpec extends FormSpec {

  val requiredKey = "$className;format="decap"$.error.required"
  val invalidKey = "error.boolean"

  val formProvider = new $className$FormProvider()

  "$className$ Form Provider" must {

    "bind true" in {
      val form = formProvider().bind(Map("value" -> "true"))
      form.get shouldBe true
    }

    "bind false" in {
      val form = formProvider().bind(Map("value" -> "false"))
      form.get shouldBe false
    }

    "fail to bind non-booleans" in {
      val expectedError = error("value", invalidKey)
      checkForError(formProvider(), Map("value" -> "not a boolean"), expectedError)
    }

    "fail to bind a blank value" in {
      val expectedError = error("value", requiredKey)
      checkForError(formProvider(), Map("value" -> ""), expectedError)
    }

    "fail to bind when value is omitted" in {
      val expectedError = error("value", requiredKey)
      checkForError(formProvider(), emptyForm, expectedError)
    }
  }
}
