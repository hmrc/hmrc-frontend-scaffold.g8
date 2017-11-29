package forms

import play.api.data.{Form, FormError}
import play.api.data.Forms._
import play.api.data.format.Formatter

object $className$Form extends FormErrorHelper {

  def $className;format="decap"$Formatter(errorKeyBlank: String) = new Formatter[String] {

    def bind(key: String, data: Map[String, String]) = {
      data.get(key) match {
        case None => produceError(key, errorKeyBlank)
        case Some("") => produceError(key, errorKeyBlank)
        case Some(s) => Right(s)
      }
    }

    def unbind(key: String, value: String) = Map(key -> value)
  }

  def apply(errorKeyBlank: String = "error.required"): Form[String] =
    Form(single("value" -> of($className;format="decap"$Formatter(errorKeyBlank))))
}
