package generators

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import pages._
import play.api.libs.json.JsValue
import uk.gov.hmrc.http.cache.client.CacheMap

trait CacheMapGenerator {
  self: Generators =>

  val generators: Seq[Gen[(Page, JsValue)]] =
    Nil

  implicit lazy val arbitraryCacheMap: Arbitrary[CacheMap] =
    Arbitrary {
      for {
        cacheId <- nonEmptyString
        data    <- Gen.mapOf(oneOf(generators))
      } yield CacheMap(
        cacheId,
        data.map {
          case (k, v) => ( k.toString, v )
        }
      )
    }
}
