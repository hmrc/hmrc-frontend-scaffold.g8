package forms

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formatter
import utils.RadioOption
import models.$className$

object $className$Form extends FormErrorHelper {

  def apply(): Form[$className$] =
    Form(single("value" -> of($className;format="decap"$Formatter)))

  def options = $className$.values.map {
    value =>
      RadioOption("$className;format="decap"$", value.toString)
  }

  private def $className;format="decap"$Formatter = new Formatter[$className$] {

    def bind(key: String, data: Map[String, String]) = data.get(key) match {
      case Some(s) => $className$.withName(s)
        .map(Right.apply)
        .getOrElse(produceError(key, "error.unknown"))
      case None => produceError(key, "error.required")
    }

    def unbind(key: String, value: $className$) = Map(key -> value.toString)
  }

  private def optionIsValid(value: String) = options.exists(o => o.value == value)
}
