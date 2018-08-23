package connectors

import generators.Generators
import org.mockito.Mockito._
import org.mockito.Matchers.{eq => eqTo, _}
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, OptionValues, WordSpec}
import play.api.libs.json.{JsBoolean, JsNumber, JsString}
import repositories.{ReactiveMongoRepository, SessionRepository}
import uk.gov.hmrc.http.cache.client.CacheMap

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class MongoCacheConnectorSpec
  extends WordSpec with MustMatchers with PropertyChecks with Generators with MockitoSugar with ScalaFutures with OptionValues {

  ".save" must {

    "save the cache map to the Mongo repository" in {

      val mockReactiveMongoRepository = mock[ReactiveMongoRepository]
      val mockSessionRepository = mock[SessionRepository]

      when(mockSessionRepository.apply()) thenReturn mockReactiveMongoRepository
      when(mockReactiveMongoRepository.upsert(any[CacheMap])) thenReturn Future.successful(true)

      val mongoCacheConnector = new MongoCacheConnector(mockSessionRepository)

      forAll(arbitrary[CacheMap]) {
        cacheMap =>

          val result = mongoCacheConnector.save(cacheMap)

          whenReady(result) {
            savedCacheMap =>

              savedCacheMap mustEqual cacheMap
              verify(mockReactiveMongoRepository).upsert(cacheMap)
          }
      }
    }
  }

  ".fetch" when {

    "there isn't a record for this key in Mongo" must {

      "return None" in {

        val mockReactiveMongoRepository = mock[ReactiveMongoRepository]
        val mockSessionRepository = mock[SessionRepository]

        when(mockSessionRepository.apply()) thenReturn mockReactiveMongoRepository
        when(mockReactiveMongoRepository.get(any())) thenReturn Future.successful(None)

        val mongoCacheConnector = new MongoCacheConnector(mockSessionRepository)

        forAll(nonEmptyString) {
          cacheId =>

            val result = mongoCacheConnector.fetch(cacheId)

            whenReady(result) {
              optionalCacheMap =>

                optionalCacheMap must be(empty)
            }
        }
      }
    }

    "a record exists for this key" must {

      "return the record" in {

        val mockReactiveMongoRepository = mock[ReactiveMongoRepository]
        val mockSessionRepository = mock[SessionRepository]

        when(mockSessionRepository.apply()) thenReturn mockReactiveMongoRepository

        val mongoCacheConnector = new MongoCacheConnector(mockSessionRepository)

        forAll(arbitrary[CacheMap]) {
          cacheMap =>

            when(mockReactiveMongoRepository.get(eqTo(cacheMap.id))) thenReturn Future.successful(Some(cacheMap))

            val result = mongoCacheConnector.fetch(cacheMap.id)

            whenReady(result) {
              optionalCacheMap =>

                optionalCacheMap.value mustEqual cacheMap
            }
        }
      }
    }
  }

  ".getEntry" when {

    "there isn't a record for this key in Mongo" must {

      "return None" in {

        val mockReactiveMongoRepository = mock[ReactiveMongoRepository]
        val mockSessionRepository = mock[SessionRepository]

        when(mockSessionRepository.apply()) thenReturn mockReactiveMongoRepository
        when(mockReactiveMongoRepository.get(any())) thenReturn Future.successful(None)

        val mongoCacheConnector = new MongoCacheConnector(mockSessionRepository)

        forAll(nonEmptyString, nonEmptyString) {
          (cacheId, key) =>

            val result = mongoCacheConnector.getEntry[String](cacheId, key)

            whenReady(result) {
              optionalValue =>

                optionalValue must be(empty)
            }
        }
      }
    }

    "a record exists in Mongo but this key is not present" must {

      "return None" in {

        val mockReactiveMongoRepository = mock[ReactiveMongoRepository]
        val mockSessionRepository = mock[SessionRepository]

        when(mockSessionRepository.apply()) thenReturn mockReactiveMongoRepository

        val mongoCacheConnector = new MongoCacheConnector(mockSessionRepository)

        val gen = for {
          key      <- nonEmptyString
          cacheMap <- arbitrary[CacheMap]
        } yield (key, cacheMap copy (data = cacheMap.data - key))

        forAll(gen) {
          case (key, cacheMap) =>

            when(mockReactiveMongoRepository.get(eqTo(cacheMap.id))) thenReturn Future.successful(Some(cacheMap))

            val result = mongoCacheConnector.getEntry[String](cacheMap.id, key)

            whenReady(result) {
              optionalValue =>

                optionalValue must be(empty)
            }
        }
      }
    }

    "a record exists in Mongo with this key" must {

      "return the key's value" in {

        val mockReactiveMongoRepository = mock[ReactiveMongoRepository]
        val mockSessionRepository = mock[SessionRepository]

        when(mockSessionRepository.apply()) thenReturn mockReactiveMongoRepository

        val mongoCacheConnector = new MongoCacheConnector(mockSessionRepository)

        val gen = for {
          key      <- nonEmptyString
          value    <- nonEmptyString
          cacheMap <- arbitrary[CacheMap]
        } yield (key, value, cacheMap copy (data = cacheMap.data + (key -> JsString(value))))

        forAll(gen) {
          case (key, value, cacheMap) =>

            when(mockReactiveMongoRepository.get(eqTo(cacheMap.id))) thenReturn Future.successful(Some(cacheMap))

            val result = mongoCacheConnector.getEntry[String](cacheMap.id, key)

            whenReady(result) {
              optionalValue =>

                optionalValue.value mustEqual value
            }
        }
      }
    }
  }
}
