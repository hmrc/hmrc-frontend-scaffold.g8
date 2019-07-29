package pages.behaviours

import generators.Generators
import models.UserAnswers
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import org.scalatest.{MustMatchers, OptionValues, TryValues, WordSpec}
import pages.QuestionPage
import play.api.libs.json._

trait PageBehaviours extends WordSpec with MustMatchers with ScalaCheckPropertyChecks with Generators with OptionValues with TryValues {

  class BeRetrievable[A] {
    def apply[P <: QuestionPage[A]](genP: Gen[P])(implicit ev1: Arbitrary[A], ev2: Format[A]): Unit = {

      "return None" when {

        "being retrieved from UserAnswers" when {

          "the question has not been answered" in {

            val gen = for {
              page        <- genP
              userAnswers <- arbitrary[UserAnswers]
            } yield (page, userAnswers.remove(page).success.value)

            forAll(gen) {
              case (page, userAnswers) =>

                userAnswers.get(page) must be(empty)
            }
          }
        }
      }

      "return the saved value" when {

        "being retrieved from UserAnswers" when {

          "the question has been answered" in {

            val gen = for {
              page        <- genP
              savedValue  <- arbitrary[A]
              userAnswers <- arbitrary[UserAnswers]
            } yield (page, savedValue, userAnswers.set(page, savedValue).success.value)

            forAll(gen) {
              case (page, savedValue, userAnswers) =>

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
          page        <- genP
          newValue    <- arbitrary[A]
          userAnswers <- arbitrary[UserAnswers]
        } yield (page, newValue, userAnswers)

        forAll(gen) {
          case (page, newValue, userAnswers) =>

            val updatedAnswers = userAnswers.set(page, newValue).success.value
            updatedAnswers.get(page).value mustEqual newValue
        }
      }
    }
  }

  class BeRemovable[A] {
    def apply[P <: QuestionPage[A]](genP: Gen[P])(implicit ev1: Arbitrary[A], ev2: Format[A]): Unit = {

      "be able to be removed from UserAnswers" in {

        val gen = for {
          page        <- genP
          savedValue  <- arbitrary[A]
          userAnswers <- arbitrary[UserAnswers]
        } yield (page, userAnswers.set(page, savedValue).success.value)

        forAll(gen) {
          case (page, userAnswers) =>

            val updatedAnswers = userAnswers.remove(page).success.value
            updatedAnswers.get(page) must be(empty)
        }
      }
    }
  }

  def beRetrievable[A]: BeRetrievable[A] = new BeRetrievable[A]

  def beSettable[A]: BeSettable[A] = new BeSettable[A]

  def beRemovable[A]: BeRemovable[A] = new BeRemovable[A]
}
