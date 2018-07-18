package pages

import controllers.routes
import generators.Generators
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import models.{CheckMode, NormalMode, $className$}
import org.scalacheck.Gen
import pages.behaviours.PageBehaviours
import uk.gov.hmrc.http.cache.client.CacheMap
import utils.UserAnswers

class $className$Spec extends PageBehaviours with Generators {

  "YourLocation" must {

    beRetrievable[$className$]($className$Page)

    beSettable[$className$]($className$Page)

    beRemovable[$className$]($className$Page)
  }

  ".nextPage" when {

    "in Normal mode" must {

      "go to the Index page" in {

        val gen = for {
          cacheMap <- arbitrary[CacheMap]
          original <- Gen.option(arbitrary[$className$])
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
          original <- Gen.option(arbitrary[$className$])
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
