package models

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, OptionValues, WordSpec}
import play.api.libs.json.{JsError, JsString, Json}

class $className$Spec extends WordSpec with MustMatchers with PropertyChecks with OptionValues {

  "$className$" must {

    "deserialise valid values" in {

      val gen = Gen.oneOf($className$.values.toSeq)

      forAll(gen) {
        $className;format="decap"$ =>

          JsString($className;format="decap"$.toString).validate[$className$].asOpt.value mustEqual $className;format="decap"$
      }
    }

    "fail to deserialise invalid values" in {

      val gen = arbitrary[String] suchThat (!$className$.values.map(_.toString).contains(_))

      forAll(gen) {
        invalidValue =>

          JsString(invalidValue).validate[$className$] mustEqual JsError("Unknown $className;format="decap"$")
      }
    }

    "serialise" in {

      val gen = Gen.oneOf($className$.values.toSeq)

      forAll(gen) {
        $className;format="decap"$ =>

          Json.toJson($className;format="decap"$) mustEqual JsString($className;format="decap"$.toString)
      }
    }
  }
}
