package viewmodels.govuk

import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import viewmodels.LabelSize

trait LabelFluency {

  object LabelViewModel {

    def apply(content: Content): Label =
      Label(content = content)
  }

  implicit class FluentLabel(label: Label) {

    def asPageHeading(size: LabelSize = LabelSize.ExtraLarge): Label =
      label
        .copy(isPageHeading = true)
        .withCssClass(size.toString)

    def withCssClass(className: String): Label =
      label copy (classes = s"\${label.classes} \$className")

    def withAttribute(attribute: (String, String)): Label =
      label copy (attributes = label.attributes + attribute)

    def forAttr(attr: String): Label =
      label copy (forAttr = Some(attr))
  }
}
