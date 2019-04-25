package models

import generators.ModelGenerators
import org.scalacheck.{Gen, Shrink}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FreeSpec, MustMatchers, OptionValues}
import play.api.libs.json._

class RichJsValueSpec extends FreeSpec with MustMatchers with PropertyChecks with OptionValues with ModelGenerators {

  implicit def dontShrink[A]: Shrink[A] = Shrink.shrinkAny

  val min = 2
  val max = 10
  val nonEmptyAlphaStr: Gen[String] = Gen.alphaStr.suchThat(_.nonEmpty)

  def buildJsObj[B](keys: Seq[String], values: Seq[B])(implicit writes: Writes[B]): JsObject = {
    keys.zip(values).foldLeft(JsObject.empty) {
      case (acc, (key, value)) => acc + (key -> Json.toJson[B](value))
    }
  }

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

  "remove" - {
    "must return an error if the path is empty" in {

      val value = Json.obj()

      value.set(JsPath, Json.obj()) mustEqual JsError("path cannot be empty")
    }


    "must return an error if the path does not contain a value" in {

      val gen = for {
        originalKey   <- nonEmptyAlphaStr
        originalValue <- nonEmptyAlphaStr
        pathKey       <- nonEmptyAlphaStr suchThat (_ != originalKey)
      } yield (originalKey, originalValue, pathKey)

      forAll(gen) {
        case (originalKey, originalValue, pathKey) =>

          val value = Json.obj(originalKey -> originalValue)

          val path = JsPath \ pathKey

          value.remove(path) mustEqual JsError("cannot find value at path")

      }

    }

    "must remove a value given a keyPathNode and return the new object" in {

      val gen = for {
        keys   <- Gen.listOf(nonEmptyAlphaStr)
        values <- Gen.listOf(nonEmptyAlphaStr)
        keyToRemove   <- nonEmptyAlphaStr
        valueToRemove <- nonEmptyAlphaStr
      } yield (keys, values, keyToRemove, valueToRemove)

      forAll(gen) {
        case (keys, values, keyToRemove, valueToRemove) =>

          val initialObj: JsObject = keys.zip(values).foldLeft(JsObject.empty) {
            case (acc, (key, value)) => acc + (key -> JsString(value))
          }

          val testObject: JsObject = initialObj + (keyToRemove -> Json.toJson(valueToRemove))

          val pathToRemove = JsPath \ keyToRemove

          testObject mustNot equal(initialObj)
          testObject.remove(pathToRemove) mustEqual JsSuccess(initialObj)
      }
    }

    "must remove a value given an index node and return the new object for one array" in {

      val gen = for {
        key    <- nonEmptyAlphaStr
        values <- Gen.nonEmptyListOf(nonEmptyAlphaStr)
        index  <- Gen.choose(0, values.size - 1)
      } yield (key, values, index)

      forAll(gen) {
        case (key: String, values: List[String], indexToRemove: Int) =>

          val valuesInArrays: Seq[JsValue] = values.map(Json.toJson[String])
          val initialObj: JsObject = buildJsObj(Seq(key), Seq(valuesInArrays))


          val pathToRemove = JsPath \ key \ indexToRemove

          val removed: JsResult[JsValue] = initialObj.remove(pathToRemove)

          val expectedOutcome =
            buildJsObj(
              Seq(key),
              Seq(valuesInArrays.slice(0, indexToRemove) ++ valuesInArrays.slice(indexToRemove + 1, values.length)
              )
            )

          removed mustBe JsSuccess(expectedOutcome)
      }
    }

    "remove a value from one of many arrays" in {

      val input = Json.obj(
        "key" -> JsArray(Seq(Json.toJson(1), Json.toJson(2))),
        "key2" -> JsArray(Seq(Json.toJson(1), Json.toJson(2)))
      )

      val path = JsPath \ "key" \ 0

      input.remove(path) mustBe JsSuccess(
        Json.obj(
          "key" -> JsArray(Seq(Json.toJson (2))), "key2" -> JsArray(Seq(Json.toJson(1), Json.toJson(2))))
      )
    }
  }

  "remove a value when there are nested arrays" in {

    val input = Json.obj(
      "key" -> JsArray(Seq(JsArray(Seq(Json.toJson(1), Json.toJson(2))), Json.toJson(2))),
      "key2" -> JsArray(Seq(Json.toJson(1), Json.toJson(2)))
    )

    val path = JsPath \ "key" \ 0 \ 0

    input.remove(path) mustBe JsSuccess(
      Json.obj(
        "key" -> JsArray(Seq(JsArray(Seq(Json.toJson(2))), Json.toJson(2))),
        "key2" -> JsArray(Seq(Json.toJson(1), Json.toJson(2)))
      )
    )
  }

  "remove the value if the last value is deleted from an array" in {
    val input = Json.obj(
      "key" -> JsArray(Seq(Json.toJson(1))),
      "key2" -> JsArray(Seq(Json.toJson(1), Json.toJson(2)))
    )

    val path = JsPath \ "key" \ 0

    input.remove(path) mustBe JsSuccess(
      Json.obj(
        "key" -> JsArray(),
        "key2" -> JsArray(Seq(Json.toJson(1), Json.toJson(2)))
      )
    )
  }
}
