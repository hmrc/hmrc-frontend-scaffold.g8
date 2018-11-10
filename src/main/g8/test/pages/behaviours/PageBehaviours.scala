package pages.behaviours

import generators.Generators
import models.{UserAnswers, UserData}
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, OptionValues, WordSpec}
import pages.QuestionPage
import play.api.libs.json._

trait PageBehaviours extends WordSpec with MustMatchers with PropertyChecks with Generators with OptionValues {

  class BeRetrievable[A] {
    def apply[P <: QuestionPage[A]](genP: Gen[P])(implicit ev1: Arbitrary[A], ev2: Format[A]): Unit = {

      "return None" when {

        "being retrieved from UserAnswers" when {

          "the question has not been answered" in {

            val gen = for {
              page     <- genP
              userData <- arbitrary[UserData]
            } yield (page, userData copy (data = userData.data - page.toString))

            forAll(gen) {
              case (page, userData) =>

                whenever(!userData.data.keys.contains(page.toString)) {

                  val userAnswers = UserAnswers(userData)
                  userAnswers.get(page) must be(empty)
                }
            }
          }
        }
      }

      "return the saved value" when {

        "being retrieved from UserAnswers" when {

          "the question has been answered" in {

            val gen = for {
              page       <- genP
              savedValue <- arbitrary[A]
              userData   <- arbitrary[UserData]
            } yield (page, savedValue, userData copy (data = userData.data + (page.toString -> Json.toJson(savedValue))))

            forAll(gen) {
              case (page, savedValue, userData) =>

                val userAnswers = UserAnswers(userData)
                userAnswers.get(page).value mustEqual savedValue
            }
          }
        }
      }
    }
  }

  class BeSettable[A] {
    def apply[P <: QuestionPage[A]](genP: Gen[P])(implicit ev1: Arbitrary[A], ev2: Format[A]): Unit = {

      "be able to be set on UserAnswers" in {

        val gen = for {
          page     <- genP
          newValue <- arbitrary[A]
          userData <- arbitrary[UserData]
        } yield (page, newValue, userData)

        forAll(gen) {
          case (page, newValue, userData) =>

            val userAnswers = UserAnswers(userData)
            val updatedAnswers = userAnswers.set(page, newValue)
            updatedAnswers.get(page).value mustEqual newValue
        }
      }
    }
  }

  class BeRemovable[A] {
    def apply[P <: QuestionPage[A]](genP: Gen[P])(implicit ev1: Arbitrary[A], ev2: Format[A]): Unit = {

      "be able to be removed from UserAnswers" in {

        val gen = for {
          page       <- genP
          savedValue <- arbitrary[A]
          userData   <- arbitrary[UserData]
        } yield (page, userData copy (data = userData.data + (page.toString -> Json.toJson(savedValue))))

        forAll(gen) {
          case (page, userData) =>

            val userAnswers = UserAnswers(userData)
            val updatedAnswers = userAnswers.remove(page)
            updatedAnswers.get(page) must be(empty)
        }
      }
    }
  }

  def beRetrievable[A]: BeRetrievable[A] = new BeRetrievable[A]

  def beSettable[A]: BeSettable[A] = new BeSettable[A]

  def beRemovable[A]: BeRemovable[A] = new BeRemovable[A]
}
