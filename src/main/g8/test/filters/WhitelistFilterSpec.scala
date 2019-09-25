package filters

import akka.stream.Materializer
import com.typesafe.config.ConfigException
import generators.Generators
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import org.scalatest.{FreeSpec, MustMatchers}
import play.api.Configuration
import play.api.mvc.Call

class WhitelistFilterSpec extends FreeSpec with MustMatchers with ScalaCheckPropertyChecks with MockitoSugar with Generators {

  val mockMaterializer = mock[Materializer]

  val otherConfigGen = Gen.mapOf[String, String](
    for {
      key   <- Gen.alphaNumStr suchThat (_.nonEmpty)
      value <- arbitrary[String]
    } yield (key, value)
  )

  "the list of whitelisted IP addresses" - {

    "must throw an exception" - {

      "when the underlying config value is not there" in {

        forAll(otherConfigGen, arbitrary[String], arbitrary[String]) {
          (otherConfig, destination, excluded) =>

            whenever(!otherConfig.contains("filters.whitelist.ips")) {

              val config = Configuration(
                (otherConfig +
                  ("filters.whitelist.destination" -> destination) +
                  ("filters.whitelist.excluded"    -> excluded)
                ).toSeq: _*
              )

              assertThrows[ConfigException] {
                new WhitelistFilter(config, mockMaterializer)
              }
            }
        }
      }
    }

    "must be empty" - {

      "when the underlying config value is empty" in {

        forAll(otherConfigGen, arbitrary[String], arbitrary[String]) {
          (otherConfig, destination, excluded) =>

            val config = Configuration(
              (otherConfig +
                ("filters.whitelist.destination" -> destination) +
                ("filters.whitelist.excluded"    -> excluded) +
                ("filters.whitelist.ips"         -> "")
              ).toSeq: _*
            )

            val whitelistFilter = new WhitelistFilter(config, mockMaterializer)

            whitelistFilter.whitelist mustBe empty
          }
      }
    }

    "must contain all of the values" - {

      "when given a comma-separated list of values" in {

        val gen = Gen.nonEmptyListOf(Gen.alphaNumStr suchThat (_.nonEmpty))

        forAll(gen, otherConfigGen, arbitrary[String], arbitrary[String]) {
          (ips, otherConfig, destination, excluded) =>

            val ipString = ips.mkString(",")

            val config = Configuration(
              (otherConfig +
                ("filters.whitelist.destination" -> destination) +
                ("filters.whitelist.excluded"    -> excluded) +
                ("filters.whitelist.ips"         -> ipString)
              ).toSeq: _*
            )

            val whitelistFilter = new WhitelistFilter(config, mockMaterializer)

            whitelistFilter.whitelist must contain theSameElementsAs ips
        }
      }
    }
  }

  "the destination for non-whitelisted visitors" - {

    "must throw an exception" - {

      "when the underlying config value is not there" in {

        forAll(otherConfigGen, arbitrary[String], arbitrary[String]) {
          (otherConfig, destination, excluded) =>

            whenever(!otherConfig.contains("filters.whitelist.destination")) {

              val config = Configuration(
                (otherConfig +
                  ("filters.whitelist.ips"      -> destination) +
                  ("filters.whitelist.excluded" -> excluded)
                  ).toSeq: _*
              )

              assertThrows[ConfigException] {
                new WhitelistFilter(config, mockMaterializer)
              }
            }
        }
      }
    }

    "must return a Call to the destination" in {

      forAll(otherConfigGen, arbitrary[String], arbitrary[String], arbitrary[String]) {
        (otherConfig, ips, destination, excluded) =>

          val config = Configuration(
            (otherConfig +
              ("filters.whitelist.ips"         -> destination) +
              ("filters.whitelist.excluded"    -> excluded) +
              ("filters.whitelist.destination" -> destination)
              ).toSeq: _*
          )

          val whitelistFilter = new WhitelistFilter(config, mockMaterializer)

          whitelistFilter.destination mustEqual Call("GET", destination)
      }
    }
  }

  "the list of excluded paths" - {

    "must throw an exception" - {

      "when the underlying config value is not there" in {

        forAll(otherConfigGen, arbitrary[String], arbitrary[String]) {
          (otherConfig, destination, excluded) =>

            whenever(!otherConfig.contains("filters.whitelist.excluded")) {

              val config = Configuration(
                (otherConfig +
                  ("filters.whitelist.destination" -> destination) +
                  ("filters.whitelist.ips"    -> excluded)
                  ).toSeq: _*
              )

              assertThrows[ConfigException] {
                new WhitelistFilter(config, mockMaterializer)
              }
            }
        }
      }
    }

    "must return Calls to all of the values" - {

      "when given a comma-separated list of values" in {

        val gen = Gen.nonEmptyListOf(Gen.alphaNumStr suchThat (_.nonEmpty))

        forAll(gen, otherConfigGen, arbitrary[String], arbitrary[String]) {
          (excludedPaths, otherConfig, destination, ips) =>

            val excludedPathString = excludedPaths.mkString(",")

            val config = Configuration(
              (otherConfig +
                ("filters.whitelist.destination" -> destination) +
                ("filters.whitelist.excluded"    -> excludedPathString) +
                ("filters.whitelist.ips"         -> ips)
                ).toSeq: _*
            )

            val expectedCalls = excludedPaths.map(Call("GET", _))

            val whitelistFilter = new WhitelistFilter(config, mockMaterializer)

            whitelistFilter.excludedPaths must contain theSameElementsAs expectedCalls
        }
      }
    }
  }
}
