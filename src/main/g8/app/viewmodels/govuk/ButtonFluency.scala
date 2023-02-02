package viewmodels.govuk

import uk.gov.hmrc.govukfrontend.views.viewmodels.button.Button
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content

object button extends ButtonFluency

trait ButtonFluency {

  object ButtonViewModel {

    def apply(content: Content): Button =
      Button(
        element = Some("button"),
        content = content
      )
  }

  implicit class FluentButton(button: Button) {

    def asLink(href: String): Button =
      button.copy(
        element = Some("a"),
        href    = Some(href)
      )

    def asInput(inputType: String): Button =
      button.copy(
        element   = Some("input"),
        inputType = Some(inputType)
      )

    def withName(name: String): Button =
      button copy (name = Some(name))

    def withCssClass(newClass: String): Button =
      button copy (classes = s"\${button.classes} \$newClass")

    def withAttribute(attribute: (String, String)): Button =
      button copy (attributes = button.attributes + attribute)

    def disabled(): Button =
      button copy (disabled = true)

    def preventingDoubleClick(): Button =
      button copy (preventDoubleClick = Some(true))

    def asStartButton(): Button =
      button copy (isStartButton = true)

    def asSecondaryButton(): Button =
      withCssClass("govuk-button--secondary")

    def asWarningButton(): Button =
      withCssClass("govuk-button--warning")
  }
}
