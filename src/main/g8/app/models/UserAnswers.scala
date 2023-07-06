package models

import play.api.libs.functional.syntax._
import play.api.libs.json._
import queries.{Gettable, Settable}
import uk.gov.hmrc.crypto.Sensitive.SensitiveString
import uk.gov.hmrc.crypto.json.JsonEncryption
import uk.gov.hmrc.crypto.{Decrypter, Encrypter}
import uk.gov.hmrc.mongo.play.json.formats.MongoJavatimeFormats

import java.time.Instant
import scala.util.{Failure, Success, Try}

final case class UserAnswers(
                              id: String,
                              data: JsObject = Json.obj(),
                              lastUpdated: Instant = Instant.now
                            ) {

  def get[A](page: Gettable[A])(implicit rds: Reads[A]): Option[A] =
    Reads.optionNoError(Reads.at(page.path)).reads(data).getOrElse(None)

  def set[A](page: Settable[A], value: A)(implicit writes: Writes[A]): Try[UserAnswers] = {

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

  def remove[A](page: Settable[A]): Try[UserAnswers] = {

    val updatedData = data.removeObject(page.path) match {
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

  implicit val format: OFormat[UserAnswers] = Json.format

  def encryptedFormat(implicit crypto: Encrypter with Decrypter): OFormat[UserAnswers] = {

    implicit val sensitiveFormat: Format[SensitiveString] =
      JsonEncryption.sensitiveEncrypterDecrypter(SensitiveString.apply)

    val encryptedReads: Reads[UserAnswers] =
      (
        (__ \ "userId").read[String] and
        (__ \ "data").read[SensitiveString] and
        (__ \ "lastUpdated").read(MongoJavatimeFormats.instantFormat)
      )((id, data, lastUpdated) => UserAnswers(id, Json.parse(data.decryptedValue).as[JsObject], lastUpdated))

    val encryptedWrites: OWrites[UserAnswers] =
      (
        (__ \ "userId").write[String] and
        (__ \ "data").write[SensitiveString] and
        (__ \ "lastUpdated").write(MongoJavatimeFormats.instantFormat)
      )(ua => (ua.id, SensitiveString(Json.stringify(ua.data)), ua.lastUpdated))

    OFormat(encryptedReads, encryptedWrites)
  }
}
