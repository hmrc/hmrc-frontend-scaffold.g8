package models

import java.time.LocalDateTime

import pages._
import play.api.libs.json._

import scala.util.{Failure, Success, Try}

final case class UserAnswers(
                              id: String,
                              data: JsObject = Json.obj(),
                              lastUpdated: LocalDateTime = LocalDateTime.now
                            ) {

  def get[A](page: QuestionPage[A])(implicit rds: Reads[A]): Option[A] =
    Reads.optionNoError(Reads.at(page.path)).reads(data).getOrElse(None)

  def set[A](page: QuestionPage[A], value: A)(implicit writes: Writes[A]): Try[UserAnswers] = {

    val updatedData = data.setObject(page.path, Json.toJson(value)) match {
      case JsSuccess(jsValue, _) =>
        Success(jsValue)
      case JsError(errors) =>
        Failure(JsResultException(errors))
    }

    updatedData.flatMap {
      d =>
        val updatedAnswers = copy (data = d)
        page.cleanup(Some(value), updatedAnswers)
    }
  }

  def remove[A](page: QuestionPage[A]): Try[UserAnswers] = {

    val updatedData = data.setObject(page.path, JsNull) match {
      case JsSuccess(jsValue, _) =>
        Success(jsValue)
      case JsError(_) =>
        Success(data)
    }

    updatedData.flatMap {
      d =>
        val updatedAnswers = copy (data = d)
        page.cleanup(None, updatedAnswers)
    }
  }
}

object UserAnswers {

  implicit lazy val reads: Reads[UserAnswers] = {

    import play.api.libs.functional.syntax._

    (
      (__ \ "_id").read[String] and
      (__ \ "data").read[JsObject] and
      (__ \ "lastUpdated").read(MongoDateTimeFormats.localDateTimeRead)
    ) (UserAnswers.apply _)
  }

  implicit lazy val writes: OWrites[UserAnswers] = {

    import play.api.libs.functional.syntax._

    (
      (__ \ "_id").write[String] and
      (__ \ "data").write[JsObject] and
      (__ \ "lastUpdated").write(MongoDateTimeFormats.localDateTimeWrite)
    ) (unlift(UserAnswers.unapply))
  }
}
