package pages.behaviours

import generators.Generators
import models.UserAnswers
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import org.scalatest.{OptionValues, TryValues}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import pages.QuestionPage
import play.api.libs.json._

trait PageBehaviours extends AnyFreeSpec with Matchers with ScalaCheckPropertyChecks with Generators with OptionValues with TryValues {

  class BeRetrievable[A] {
    def apply[P <: QuestionPage[A]](genP: Gen[P])(implicit ev1: Arbitrary[A], ev2: Format[A]): Unit = {

      "when being retrieved from UserAnswers" - {

        "and the question has not been answered" - {

          "must return None" in {
            
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

        "and the question has been answered" - {

          "must return the saved value" in {

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

      "must be able to be set on UserAnswers" in {

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

      "must be able to be removed from UserAnswers" in {

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
