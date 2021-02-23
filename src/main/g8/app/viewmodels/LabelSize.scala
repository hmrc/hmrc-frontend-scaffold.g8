package viewmodels

sealed trait LabelSize

object LabelSize {
  case object ExtraLarge extends WithCssClass("govuk-label--xl") with LabelSize
  case object Large      extends WithCssClass("govuk-label--l") with LabelSize
  case object Medium     extends WithCssClass("govuk-label--m") with LabelSize
  case object Small      extends WithCssClass("govuk-label--s") with LabelSize
}
