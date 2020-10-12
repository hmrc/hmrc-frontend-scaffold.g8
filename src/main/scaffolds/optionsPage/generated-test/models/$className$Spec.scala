package models

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.OptionValues
import play.api.libs.json.{JsError, JsString, Json}

class $className$Spec extends AnyFreeSpec with Matchers with ScalaCheckPropertyChecks with OptionValues {

  "$className$" - {

    "must deserialise valid values" in {

      val gen = Gen.oneOf($className$.values.toSeq)

      forAll(gen) {
        $className;format="decap"$ =>

          JsString($className;format="decap"$.toString).validate[$className$].asOpt.value mustEqual $className;format="decap"$
      }
    }

    "must fail to deserialise invalid values" in {

      val gen = arbitrary[String] suchThat (!$className$.values.map(_.toString).contains(_))

      forAll(gen) {
        invalidValue =>

          JsString(invalidValue).validate[$className$] mustEqual JsError("error.invalid")
      }
    }

    "must serialise" in {

      val gen = Gen.oneOf($className$.values.toSeq)

      forAll(gen) {
        $className;format="decap"$ =>

          Json.toJson($className;format="decap"$) mustEqual JsString($className;format="decap"$.toString)
      }
    }
  }
}
