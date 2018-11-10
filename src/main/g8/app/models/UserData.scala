package models

import java.time.LocalDateTime

import play.api.libs.json.{JsObject, OWrites, Reads, __}

case class UserData(
                     id: String,
                     data: JsObject,
                     lastUpdated: LocalDateTime = LocalDateTime.now
                   ) {

  def getEntry[T](key: String)(implicit reads: Reads[T]): Option[T] = {
    (data \ key).validate[T].asOpt
  }
}

object UserData {

  implicit lazy val reads: Reads[UserData] = {

    import play.api.libs.functional.syntax._

    (
      (__ \ "_id").read[String] and
      (__ \ "data").read[JsObject] and
      (__ \ "lastUpdated").read[LocalDateTime]
    ) (UserData.apply _)
  }

  implicit lazy val writes: OWrites[UserData] = {

    import play.api.libs.functional.syntax._

    (
      (__ \ "_id").write[String] and
      (__ \ "data").write[JsObject] and
      (__ \ "lastUpdated").write[LocalDateTime]
    ) (unlift(UserData.unapply))
  }
}
