package forms.mappings

import play.api.data.FormError
import play.api.data.format.Formatter
import utils.Enumerable

import scala.util.control.Exception.nonFatalCatch

trait Formatters {

  private[mappings] def stringFormatter(errorKey: String): Formatter[String] = new Formatter[String] {

    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], String] =
      data.get(key) match {
        case None | Some("") => Left(Seq(FormError(key, errorKey)))
        case Some(s) => Right(s)
      }

    override def unbind(key: String, value: String): Map[String, String] =
      Map(key -> value)
  }

  private[mappings] def booleanFormatter(requiredKey: String, invalidKey: String): Formatter[Boolean] =
    new Formatter[Boolean] {

      private val baseFormatter = stringFormatter(requiredKey)

      override def bind(key: String, data: Map[String, String]) =
        baseFormatter
          .bind(key, data)
          .right.flatMap {
          case "true" => Right(true)
          case "false" => Right(false)
          case _ => Left(Seq(FormError(key, invalidKey)))
        }

      def unbind(key: String, value: Boolean) = Map(key -> value.toString)
    }

  private[mappings] def intFormatter(requiredKey: String, wholeNumberKey: String, nonNumericKey: String): Formatter[Int] =
    new Formatter[Int] {

      val decimalRegexp = """^(\d*\.\d*)\$"""

      private val baseFormatter = stringFormatter(requiredKey)

      override def bind(key: String, data: Map[String, String]) =
        baseFormatter
          .bind(key, data)
          .right.map(_.replace(",", ""))
          .right.flatMap {
          case s if s.matches(decimalRegexp) =>
            Left(Seq(FormError(key, wholeNumberKey)))
          case s =>
            nonFatalCatch
              .either(s.toInt)
              .left.map(_ => Seq(FormError(key, nonNumericKey)))
        }

      override def unbind(key: String, value: Int) =
        baseFormatter.unbind(key, value.toString)
    }

  private[mappings] def enumerableFormatter[A](requiredKey: String, invalidKey: String)(implicit ev: Enumerable[A]): Formatter[A] =
    new Formatter[A] {

      private val baseFormatter = stringFormatter(requiredKey)

      override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], A] =
        baseFormatter.bind(key, data).right.flatMap {
          str =>
            ev.withName(str).map(Right.apply).getOrElse(Left(Seq(FormError(key, invalidKey))))
        }

      override def unbind(key: String, value: A): Map[String, String] =
        baseFormatter.unbind(key, value.toString)
    }
}
