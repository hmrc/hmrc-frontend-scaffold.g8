package forms

class $className$FormSpec extends FormSpec {

  val errorKeyBlank = "blank"

  "$className$ Form" must {

    "bind a string" in {
      val form = $className$Form(errorKeyBlank).bind(Map("value" -> "answer"))
      form.get shouldBe "answer"
    }

    "fail to bind a blank value" in {
      val expectedError = error("value", errorKeyBlank)
      checkForError($className$Form(errorKeyBlank), Map("value" -> ""), expectedError)
    }

    "fail to bind when value is omitted" in {
      val expectedError = error("value", errorKeyBlank)
      checkForError($className$Form(errorKeyBlank), emptyForm, expectedError)
    }
  }
}
