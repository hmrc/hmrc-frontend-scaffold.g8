package models

import org.scalacheck.{Gen, Shrink}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FreeSpec, MustMatchers, OptionValues}
import play.api.libs.json._

class RichJsValueSpec extends FreeSpec with MustMatchers with PropertyChecks with OptionValues {

  implicit val dontShrink: Shrink[String] = Shrink.shrinkAny

  val nonEmptyAlphaStr: Gen[String] = Gen.alphaStr.suchThat(_.nonEmpty)

  "set" - {

    "must return an error if the path is empty" in {

      val value = Json.obj()

      value.set(JsPath, Json.obj()) mustEqual JsError("path cannot be empty")
    }

    "must set a value on a JsObject" in {

      val gen = for {
        originalKey   <- nonEmptyAlphaStr
        originalValue <- nonEmptyAlphaStr
        pathKey       <- nonEmptyAlphaStr suchThat (_ != originalKey)
        newValue      <- nonEmptyAlphaStr
      } yield (originalKey, originalValue, pathKey, newValue)

      forAll(gen) {
        case (originalKey, originalValue, pathKey, newValue) =>

          val value = Json.obj(originalKey -> originalValue)

          val path = JsPath \ pathKey

          value.set(path, JsString(newValue)) mustEqual JsSuccess(Json.obj(originalKey -> originalValue, pathKey -> newValue))
      }
    }

    "must set a nested value on a JsObject" in {

      val value = Json.obj(
        "foo" -> Json.obj()
      )

      val path = JsPath \ "foo" \ "bar"

      value.set(path, JsString("baz")).asOpt.value mustEqual Json.obj(
        "foo" -> Json.obj(
          "bar" -> "baz"
        )
      )
    }

    "must add a value to an empty JsArray" in {

      forAll(nonEmptyAlphaStr) {
        newValue =>

          val value = Json.arr()

          val path = JsPath \ 0

          value.set(path, JsString(newValue)) mustEqual JsSuccess(Json.arr(newValue))
      }
    }

    "must add a value to the end of a JsArray" in {

      forAll(nonEmptyAlphaStr, nonEmptyAlphaStr) {
        (oldValue, newValue) =>

          val value = Json.arr(oldValue)

          val path = JsPath \ 1

          value.set(path, JsString(newValue)) mustEqual JsSuccess(Json.arr(oldValue, newValue))
      }
    }

    "must change a value in an existing JsArray" in {

      forAll(nonEmptyAlphaStr, nonEmptyAlphaStr, nonEmptyAlphaStr) {
        (firstValue, secondValue, newValue) =>

          val value = Json.arr(firstValue, secondValue)

          val path = JsPath \ 0

          value.set(path, JsString(newValue)) mustEqual JsSuccess(Json.arr(newValue, secondValue))
      }
    }

    "must set a nested value on a JsArray" in {

      val value = Json.arr(Json.arr("foo"))

      val path = JsPath \ 0 \ 0

      value.set(path, JsString("bar")).asOpt.value mustEqual Json.arr(Json.arr("bar"))
    }

    "must change the value of an existing key" in {

      val gen = for {
        originalKey   <- nonEmptyAlphaStr
        originalValue <- nonEmptyAlphaStr
        newValue      <- nonEmptyAlphaStr
      } yield (originalKey, originalValue, newValue)

      forAll(gen) {
        case (pathKey, originalValue, newValue) =>

          val value = Json.obj(pathKey -> originalValue)

          val path = JsPath \ pathKey

          value.set(path, JsString(newValue)) mustEqual JsSuccess(Json.obj(pathKey -> newValue))
      }
    }

    "must return an error when trying to set a key on a non-JsObject" in {

      val value = Json.arr()

      val path = JsPath \ "foo"

      value.set(path, JsString("bar")) mustEqual JsError(s"cannot set a key on \$value")
    }

    "must return an error when trying to set an index on a non-JsArray" in {

      val value = Json.obj()

      val path = JsPath \ 0

      value.set(path, JsString("bar")) mustEqual JsError(s"cannot set an index on \$value")
    }

    "must return an error when trying to set an index other than zero on an empty array" in {

      val value = Json.arr()

      val path = JsPath \ 1

      value.set(path, JsString("bar")) mustEqual JsError("array index out of bounds: 1, []")
    }

    "must return an error when trying to set an index out of bounds" in {

      val value = Json.arr("bar", "baz")

      val path = JsPath \ 3

      value.set(path, JsString("fork")) mustEqual JsError("array index out of bounds: 3, [\"bar\",\"baz\"]")
    }

    "must set into an array which does not exist" in {

      val value = Json.obj()

      val path = JsPath \ "foo" \ 0

      value.set(path, JsString("bar")) mustEqual JsSuccess(Json.obj(
        "foo" -> Json.arr("bar")
      ))
    }

    "must set into an object which does not exist" in {

      val value = Json.obj()

      val path = JsPath \ "foo" \ "bar"

      value.set(path, JsString("baz")) mustEqual JsSuccess(Json.obj(
        "foo" -> Json.obj(
          "bar" -> "baz"
        )
      ))
    }

    "must set nested objects and arrays" in {

      val value = Json.obj()

      val path = JsPath \ "foo" \ 0 \ "bar" \ 0

      value.set(path, JsString("baz")) mustEqual JsSuccess(Json.obj(
        "foo" -> Json.arr(
          Json.obj(
            "bar" -> Json.arr(
              "baz"
            )
          )
        )
      ))
    }
  }
}
