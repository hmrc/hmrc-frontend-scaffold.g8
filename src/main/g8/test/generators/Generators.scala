package generators

import org.scalacheck.{Arbitrary, Gen, Shrink}
import Gen._
import Arbitrary._

trait Generators {

  implicit val dontShrink: Shrink[String] = Shrink.shrinkAny

  def genIntersperseString(gen: Gen[String],
                           value: String,
                           frequencyV: Int = 1,
                           frequencyN: Int = 10): Gen[String] = {

    val genValue: Gen[Option[String]] = Gen.frequency(frequencyN -> None, frequencyV -> Gen.const(Some(value)))

    for {
      seq1 <- gen
      seq2 <- Gen.listOfN(seq1.length, genValue)
    } yield {
      seq1.toSeq.zip(seq2).foldRight("") {
        case ((n, Some(v)), m) =>
          m + n + v
        case ((n, _), m) =>
          m + n
      }
    }
  }

  def intsInRangeWithCommas(min: Int, max: Int): Gen[String] = {
    val numberGen = choose[Int](min, max)
    genIntersperseString(numberGen.toString, ",")
  }

  def intsLargerThanMaxValue: Gen[BigInt] =
    arbitrary[BigInt] suchThat(x => x > Int.MaxValue)
  
  def intsSmallerThanMinValue: Gen[BigInt] =
    arbitrary[BigInt] suchThat(x => x < Int.MinValue)

  def nonNumerics: Gen[String] =
    alphaStr suchThat(_.size > 0)

  def decimals: Gen[String] =
    arbitrary[BigDecimal]
      .suchThat(_.abs < Int.MaxValue)
      .suchThat(!_.isValidInt)
      .map(_.formatted("%f"))

  def intsBelowValue(value: Int): Gen[Int] =
    arbitrary[Int] suchThat(_ < value)

  def intsAboveValue(value: Int): Gen[Int] =
    arbitrary[Int] suchThat(_ > value)

  def intsOutsideRange(min: Int, max: Int): Gen[Int] =
    arbitrary[Int] suchThat(x => x < min || x > max)

  def nonBooleans: Gen[String] =
    arbitrary[String]
      .suchThat (_.nonEmpty)
      .suchThat (_ != "true")
      .suchThat (_ != "false")

  def nonEmptyString: Gen[String] =
    arbitrary[String] suchThat (_.nonEmpty)

  def stringsWithMaxLength(maxLength: Int): Gen[String] =
    for {
      length <- choose(1, maxLength)
      chars <- listOfN(length, arbitrary[Char])
    } yield chars.mkString

  def stringsLongerThan(minLength: Int): Gen[String] =
    arbitrary[String] suchThat (_.length > minLength)
}
