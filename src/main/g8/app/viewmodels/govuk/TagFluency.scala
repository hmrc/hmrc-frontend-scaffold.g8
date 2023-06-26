package viewmodels.govuk

import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.tag.Tag

object tag extends TagFluency

trait TagFluency {

  object TagViewModel {

    def apply(content: Content): Tag =
      Tag(content = content)
  }

  implicit class FluentTag(tag: Tag) {

    def withCssClass(newClass: String): Tag =
      tag.copy(classes = s"\${tag.classes} \$newClass")

    def withAttribute(attribute: (String, String)): Tag =
      tag.copy(attributes = tag.attributes + attribute)

    def grey(): Tag =
      withCssClass("govuk-tag--grey")

    def green(): Tag =
      withCssClass("govuk-tag--green")

    def turquoise(): Tag =
      withCssClass("govuk-tag--turquoise")

    def blue(): Tag =
      withCssClass("govuk-tag--blue")

    def purple(): Tag =
      withCssClass("govuk-tag--purple")

    def pink(): Tag =
      withCssClass("govuk-tag--pink")

    def red(): Tag =
      withCssClass("govuk-tag--red")

    def orange(): Tag =
      withCssClass("govuk-tag--orange")

    def yellow(): Tag =
      withCssClass("govuk-tag--yellow")
  }
}
