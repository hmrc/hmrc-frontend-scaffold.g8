package matchers

import org.scalatest.exceptions.TestFailedException
import org.scalatest.{FreeSpec, MustMatchers, OptionValues, Succeeded}
import play.api.libs.json.Json

class JsonMatchersSpec extends FreeSpec with MustMatchers with JsonMatchers with OptionValues {

  "contain Json" - {

    "must return Succeeded when expected Json is an empty Json object" in {

      val json = Json.obj("foo" -> "bar")
      val expectedJson = Json.obj()

      val result = json must containJson(expectedJson)

      result mustBe Succeeded
    }

    "must return Succeeded when all keys in the expected Json are present" in {

      val json = Json.obj(
        "foo" -> 1,
        "bar" -> "baz"
      )

      val expectedJson = Json.obj("foo" -> 1)

      val result = json must containJson(expectedJson)

      result mustBe Succeeded
    }

    "must return Succeeded when both Json objects are the same" in {

      val json = Json.obj(
        "foo" -> 1,
        "bar" -> "baz"
      )

      val result = json must containJson(json)

      result mustBe Succeeded
    }

    "must throw a Test Failed Exception when expected Json contains a key not present in the left Json" in {

      val json = Json.obj("foo" -> 1)
      val expectedJson = Json.obj("bar" -> "baz")

      val ex = intercept[TestFailedException] {
        json must containJson(expectedJson)
      }

      ex.message.value mustBe """{"foo":1} did not match for key(s) bar"""
    }

    "must throw a Test Failed Exception when expected Json contains a different value for a key in the left Json" in {

      val json = Json.obj("foo" -> 1)
      val expectedJson = Json.obj("foo" -> 2)

      val ex = intercept[TestFailedException] {
        json must containJson(expectedJson)
      }

      ex.message.value mustBe """{"foo":1} did not match for key(s) foo"""
    }
  }
}
