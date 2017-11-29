package forms

import play.api.data.{Form, FormError}
import uk.gov.hmrc.play.test.UnitSpec

trait FormSpec extends UnitSpec {
  def checkForError(form: Form[_], data: Map[String, String], expectedErrors: Seq[FormError]) = {
    form.bind(data).fold(
      formWithErrors => {
        for (error <- formWithErrors.errors) expectedErrors should contain(FormError(error.key, error.message))
        formWithErrors.errors.size shouldBe expectedErrors.size
      },
      form => {
        fail("Expected a validation error when binding the form, but it was bound successfully.")
      }
    )
  }

  def error(key: String, value: String) = Seq(FormError(key, value))

  lazy val emptyForm = Map[String, String]()

}