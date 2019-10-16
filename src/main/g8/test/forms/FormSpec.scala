package forms

import org.scalatest.{FreeSpec, Matchers, OptionValues}
import play.api.data.{Form, FormError}

trait FormSpec extends FreeSpec with OptionValues with Matchers {

  def checkForError(form: Form[_], data: Map[String, String], expectedErrors: Seq[FormError]) = {
    
    form.bind(data).fold(
      formWithErrors => {
        for (error <- expectedErrors) formWithErrors.errors should contain(FormError(error.key, error.message, error.args))
        formWithErrors.errors.size shouldBe expectedErrors.size
      },
      form => {
        fail("Expected a validation error when binding the form, but it was bound successfully.")
      }
    )
  }

  def error(key: String, value: String, args: Any*) = Seq(FormError(key, value, args))

  lazy val emptyForm = Map[String, String]()
}
