package models

import play.api.libs.json._
import viewmodels.RadioOption

sealed trait $className$

object $className$ {

  case object $option1key;format="Camel"$ extends WithName("$option1key;format="decap"$") with $className$
  case object $option2key;format="Camel"$ extends WithName("$option2key;format="decap"$") with $className$

  val values: Set[$className$] = Set(
    $option1key;format="Camel"$, $option2key;format="Camel"$
  )

  val options: Set[RadioOption] = values.map {
    value =>
      RadioOption("$className;format="decap"$", value.toString)
  }

  implicit val enumerable: Enumerable[$className$] =
    Enumerable(values.toSeq.map(v => v.toString -> v): _*)

  implicit object $className$Writes extends Writes[$className$] {
    def writes($className;format="decap"$: $className$) = Json.toJson($className;format="decap"$.toString)
  }
  
  implicit object $className$Reads extends Reads[$className$] {
    override def reads(json: JsValue): JsResult[$className$] = json match {
      case JsString($option1key;format="Camel"$.toString) => JsSuccess($option1key;format="Camel"$)
      case JsString($option2key;format="Camel"$.toString) => JsSuccess($option2key;format="Camel"$)
      case _                          => JsError("Unknown $className;format="decap"$")
    }
  }
}
