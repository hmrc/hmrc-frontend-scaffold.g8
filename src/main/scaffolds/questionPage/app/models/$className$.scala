package models

import play.api.libs.json._

case class $className$ (field1: String, field2: String)

object $className$ {
  implicit val format = Json.format[$className$]
}
