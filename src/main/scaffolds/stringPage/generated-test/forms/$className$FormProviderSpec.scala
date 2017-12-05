package forms

class $className$FormProviderSpec extends FormSpec {

  val requiredKey = "$className;format="decap"$.error.required"

  "$className$ Form" must {

    val formProvider = new $className$FormProvider()

    "bind a string" in {
      val form = formProvider().bind(Map("value" -> "answer"))
      form.get shouldBe "answer"
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
