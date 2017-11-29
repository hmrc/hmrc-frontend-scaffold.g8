package utils

import play.api.libs.json._

trait Enumerable[A] {

  def values: Set[A]

  def withName(str: String): Option[A] =
    mappings.get(str)

  private lazy val mappings: Map[String, A] =
    values.map {
      value =>
        (value.toString, value)
    }.toMap

  implicit lazy val reads: Reads[A] =
    Reads {
      case JsString(str) if mappings.contains(str) =>
        JsSuccess(mappings(str))
      case _ =>
        JsError("error.invalid")
    }

  implicit lazy val writes: Writes[A] =
    Writes(value => JsString(value.toString))

  implicit lazy val formats: Format[A] = Format(reads, writes)
}
