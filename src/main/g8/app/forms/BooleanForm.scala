package forms

import play.api.data.{Form, FormError}
import play.api.data.Forms._
import play.api.data.format.Formatter

object BooleanForm extends FormErrorHelper {
  def booleanFormat(errorKey: String): Formatter[Boolean] = new Formatter[Boolean] {

    override val format = Some(("format.boolean", Nil))

    def bind(key: String, data: Map[String, String]) = {
      data.get(key) match {
        case Some("true") => Right(true)
        case Some("false") => Right(false)
        case _ => produceError(key, errorKey)
      }
    }

    def unbind(key: String, value: Boolean) = Map(key -> value.toString)
  }

  def apply(errorKey: String = "error.boolean"): Form[Boolean] = Form(single("value" -> of(booleanFormat(errorKey))))
}
