package generators

import org.scalacheck.{Arbitrary, Gen, Shrink}
import Gen._
import Arbitrary._

trait Generators {

  implicit val dontShrink: Shrink[String] = Shrink.shrinkAny

  def intersperse[A](a : List[A], b : List[A]): List[A] = a match {
    case first :: rest => first :: intersperse(b, rest)
    case _             => b
  }

  def intsInRangeWithCommas(min: Int, max: Int): Gen[String] = for {
    number         <- choose[Int](min, max)
    numberOfCommas <- choose(0, number.toString.length)
    commas         <- listOfN(numberOfCommas, const(','))
  } yield intersperse(number.toString.toList, commas).mkString

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
}
