package forms

import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import play.api.data.{Form, FormError}

trait FormSpec extends AnyFreeSpec with Matchers with OptionValues {

  def checkForError(form: Form[_], data: Map[String, String], expectedErrors: Seq[FormError]) = {
    
    form.bind(data).fold(
      formWithErrors => {
        for (error <- expectedErrors) formWithErrors.errors must contain(FormError(error.key, error.message, error.args))
        formWithErrors.errors.size mustBe expectedErrors.size
      },
      form => {
        fail("Expected a validation error when binding the form, but it was bound successfully.")
      }
    )
  }

  def error(key: String, value: String, args: Any*) = Seq(FormError(key, value, args))

  lazy val emptyForm = Map[String, String]()
}
