package forms

import scala.concurrent.ExecutionContext.Implicits.global

class BooleanFormSpec extends FormSpec {

  val errorKey = "error.key"

  "Boolean Form" must {

    "bind true" in {
      val form = BooleanForm(errorKey).bind(Map("value" -> "true"))
      form.get shouldBe true
    }

    "bind false" in {
      val form = BooleanForm(errorKey).bind(Map("value" -> "false"))
      form.get shouldBe false
    }

    "fail to bind non-booleans" in {
      val expectedError = error("value", errorKey)
      checkForError(BooleanForm(errorKey), Map("value" -> "not a boolean"), expectedError)
    }

    "fail to bind a blank value" in {
      val expectedError = error("value", errorKey)
      checkForError(BooleanForm(errorKey), Map("value" -> ""), expectedError)
    }

    "fail to bind when value is omitted" in {
      val expectedError = error("value", errorKey)
      checkForError(BooleanForm(errorKey), emptyForm, expectedError)
    }
  }
}
