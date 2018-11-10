package generators

import models.UserData
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import pages._
import play.api.libs.json.{JsValue, Json}

trait UserDataGenerator {
  self: Generators =>

  val generators: Seq[Gen[(Page, JsValue)]] =
    Nil

  implicit lazy val arbitraryUserData: Arbitrary[UserData] =
    Arbitrary {
      for {
        cacheId <- nonEmptyString
        data    <- generators match {
          case Nil => Gen.const(Map[Page, JsValue]())
          case _   => Gen.mapOf(oneOf(generators))
        }
      } yield UserData(
        cacheId,
        data.map {
          case (k, v) => Json.obj(k.toString -> v)
        }.foldLeft(Json.obj())(_ ++ _)
      )
    }
}
