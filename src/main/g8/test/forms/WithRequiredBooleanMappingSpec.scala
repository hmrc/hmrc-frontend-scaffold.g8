package forms

import play.api.data.Form
import play.api.data.Forms._

class WithRequiredBooleanMappingSpec extends FormSpec {

  "With Required Boolean Mapping" must {

    object TestForm extends WithRequiredBooleanMapping {
      def apply(): Form[Boolean] = Form(
        single(
          "value" -> requiredBoolean
        )
      )
    }

    "bind true" in {
      val form = TestForm().bind(Map("value" -> "true"))
      form.get shouldBe true
    }

    "bind false" in {
      val form = TestForm().bind(Map("value" -> "false"))
      form.get shouldBe false
    }

    "fail to bind non-booleans" in {
      val expectedError = error("value", "error.boolean")
      checkForError(TestForm(), Map("value" -> "not a boolean"), expectedError)
    }

    "fail to bind a blank value" in {
      val expectedError = error("value", "error.boolean")
      checkForError(TestForm(), Map("value" -> ""), expectedError)
    }

    "fail to bind when value is omitted" in {
      val expectedError = error("value", "error.boolean")
      checkForError(TestForm(), emptyForm, expectedError)
    }
  }
}
