package viewmodels

sealed trait InputWidth

object InputWidth {

  case object Fixed2 extends WithCssClass("govuk-input--width-2") with InputWidth
  case object Fixed3 extends WithCssClass("govuk-input--width-3") with InputWidth
  case object Fixed4 extends WithCssClass("govuk-input--width-4") with InputWidth
  case object Fixed5 extends WithCssClass("govuk-input--width-5") with InputWidth
  case object Fixed10 extends WithCssClass("govuk-input--width-10") with InputWidth
  case object Fixed20 extends WithCssClass("govuk-input--width-20") with InputWidth
  case object Fixed30 extends WithCssClass("govuk-input--width-30") with InputWidth

  case object Full extends WithCssClass("govuk-!-width-full") with InputWidth
  case object ThreeQuarters extends WithCssClass("govuk-!-width-three-quarters") with InputWidth
  case object TwoThirds extends WithCssClass("govuk-!-width-two-thirds") with InputWidth
  case object OneHalf extends WithCssClass("govuk-!-width-one-half") with InputWidth
  case object OneThird extends WithCssClass("govuk-!-width-one-third") with InputWidth
  case object OneQuarter extends WithCssClass("govuk-!-width-one-quarter") with InputWidth
}
