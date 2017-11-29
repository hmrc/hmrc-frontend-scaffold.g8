package forms

import play.api.data.{FormError, Forms, Mapping}
import play.api.data.format.Formatter

trait WithRequiredBooleanMapping {

  implicit def requiredBooleanFormatter: Formatter[Boolean] = new Formatter[Boolean] {

    override val format = Some(("format.boolean", Nil))

    def bind(key: String, data: Map[String, String]) = {
      Right(data.get(key).getOrElse("")).right.flatMap {
        case "true" => Right(true)
        case "false" => Right(false)
        case _ => Left(Seq(FormError(key, "error.boolean", Nil)))
      }
    }

    def unbind(key: String, value: Boolean) = Map(key -> value.toString)
  }

  val requiredBoolean: Mapping[Boolean] = Forms.of[Boolean](requiredBooleanFormatter)
}
