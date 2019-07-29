package models

import java.time.{LocalDate, LocalDateTime}

import org.scalatest.{FreeSpec, MustMatchers, OptionValues}
import play.api.libs.json.Json

class MongoDateTimeFormatsSpec extends FreeSpec with MustMatchers with OptionValues with MongoDateTimeFormats {

  "a LocalDateTime" - {

    val date = LocalDate.of(2018, 2, 1).atStartOfDay

    val dateMillis = 1517443200000L

    val json = Json.obj(
      "\$date" -> dateMillis
    )

    "must serialise to json" in {
      val result = Json.toJson(date)
      result mustEqual json
    }

    "must deserialise from json" in {
      val result = json.as[LocalDateTime]
      result mustEqual date
    }

    "must serialise/deserialise to the same value" in {
      val result = Json.toJson(date).as[LocalDateTime]
      result mustEqual date
    }
  }
}
