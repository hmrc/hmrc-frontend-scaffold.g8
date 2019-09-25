package models

import java.time.{Instant, LocalDateTime, ZoneOffset}

import play.api.libs.json._

trait MongoDateTimeFormats {

  implicit val localDateTimeRead: Reads[LocalDateTime] =
    (__ \ "\$date").read[Long].map {
      millis =>
        LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC)
    }

  implicit val localDateTimeWrite: Writes[LocalDateTime] = new Writes[LocalDateTime] {
    def writes(dateTime: LocalDateTime): JsValue = Json.obj(
      "\$date" -> dateTime.atZone(ZoneOffset.UTC).toInstant.toEpochMilli
    )
  }
}

object MongoDateTimeFormats extends MongoDateTimeFormats
