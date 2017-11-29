package models

import play.api.libs.json._

case class Date (day: Int, month: Int, year: Int) {}

object Date {
  implicit val format = Json.format[Date]
}

sealed abstract class DatePart
case object Day extends DatePart
case object Month extends DatePart
case object Year extends DatePart
