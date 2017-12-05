package forms

class $className$FormProviderSpec extends FormSpec {

  val errorKeyBlank = "$className;format="decap"$.error.required"
  val errorKeyDecimal = "$className;format="decap"$.error.wholeNumber"
  val errorKeyNonNumeric = "$className;format="decap"$.error.nonNumeric"
  val errorKeyMinimum = "$className;format="decap"$.error.minimum"

  "$className$ Form" must {

    val formProvider = new $className$FormProvider()

    "bind zero" in {
      val form = formProvider().bind(Map("value" -> "0"))
      form.get shouldBe 0
    }

    "bind positive numbers" in {
      val form = formProvider().bind(Map("value" -> "1"))
      form.get shouldBe 1
    }

    "bind positive, comma separated numbers" in {
      val form = formProvider().bind(Map("value" -> "10,000"))
      form.get shouldBe 10000
    }

    "fail to bind negative numbers" in {
      val expectedError = error("value", errorKeyMinimum, 0)
      checkForError(formProvider(), Map("value" -> "-1"), expectedError)
    }

    "fail to bind non-numerics" in {
      val expectedError = error("value", errorKeyNonNumeric)
      checkForError(formProvider(), Map("value" -> "not a number"), expectedError)
    }

    "fail to bind a blank value" in {
      val expectedError = error("value", errorKeyBlank)
      checkForError(formProvider(), Map("value" -> ""), expectedError)
    }

    "fail to bind when value is omitted" in {
      val expectedError = error("value", errorKeyBlank)
      checkForError(formProvider(), emptyForm, expectedError)
    }

    "fail to bind decimal numbers" in {
      val expectedError = error("value", errorKeyDecimal)
      checkForError(formProvider(), Map("value" -> "123.45"), expectedError)
    }
  }
}
