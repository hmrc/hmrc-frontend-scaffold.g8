package forms

import play.api.data.{Form, FormError}
import play.api.data.Forms._
import play.api.data.format.Formatter

object $className$Form extends FormErrorHelper {

  def $className;format="decap"$Formatter(errorKeyBlank: String, errorKeyDecimal: String, errorKeyNonNumeric: String) = new Formatter[Int] {

    val intRegex = """^(\d+)\$""".r
    val decimalRegex = """^(\d*\.\d*)\$""".r

    def bind(key: String, data: Map[String, String]) = {
      data.get(key) match {
        case None => produceError(key, errorKeyBlank)
        case Some("") => produceError(key, errorKeyBlank)
        case Some(s) => s.trim.replace(",", "") match {
          case intRegex(str) => Right(str.toInt)
          case decimalRegex(_) => produceError(key, errorKeyDecimal)
          case _ => produceError(key, errorKeyNonNumeric)
        }
      }
    }

    def unbind(key: String, value: Int) = Map(key -> value.toString)
  }

  def apply(errorKeyBlank: String = "error.required", errorKeyDecimal: String = "error.integer", errorKeyNonNumeric: String = "error.non_numeric"): Form[Int] =
    Form(single("value" -> of($className;format="decap"$Formatter(errorKeyBlank, errorKeyDecimal, errorKeyNonNumeric))))
}
