package pages

import controllers.routes
import generators.Generators
import org.scalacheck.Arbitrary.arbitrary
import models.{CheckMode, NormalMode}
import org.scalacheck.Gen
import pages.behaviours.PageBehaviours
import uk.gov.hmrc.http.cache.client.CacheMap
import utils.UserAnswers

class $className$PageSpec extends PageBehaviours with Generators {

  "$className$Page" must {

    beRetrievable[Int]($className$Page)

    beSettable[Int]($className$Page)

    beRemovable[Int]($className$Page)
  }

  ".nextPage" when {

    "in Normal mode" must {

      "go to the Index page" in {

        val gen = for {
          cacheMap <- arbitrary[CacheMap]
          original <- Gen.option(arbitrary[Int])
        } yield (cacheMap, original)

        forAll(gen) {
          case (cacheMap, original) =>

            val result = $className$Page.nextPage(NormalMode, UserAnswers(cacheMap), original)
            result mustEqual routes.IndexController.onPageLoad()
        }
      }
    }

    "in Check mode" must {

      "go to the Check Your Answers page" in {

        val gen = for {
          cacheMap <- arbitrary[CacheMap]
          original <- Gen.option(arbitrary[Int])
        } yield (cacheMap, original)

        forAll(gen) {
          case (cacheMap, original) =>

            val result = $className$Page.nextPage(CheckMode, UserAnswers(cacheMap), original)
            result mustEqual routes.CheckYourAnswersController.onPageLoad()
        }
      }
    }
  }
}
