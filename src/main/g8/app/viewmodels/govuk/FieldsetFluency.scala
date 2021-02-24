package viewmodels.govuk

import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.{Fieldset, Legend}
import viewmodels.LegendSize

trait FieldsetFluency {

  object FieldsetViewModel {

    def apply(legend: Legend): Fieldset =
      Fieldset(legend = Some(legend))
  }

  implicit class FluentFieldset(fieldset: Fieldset) {

    def describedBy(value: String): Fieldset =
      fieldset copy (describedBy = Some(value))

    def withCssClass(newClass: String): Fieldset =
      fieldset copy (classes = s"\${fieldset.classes} \$newClass")

    def withRole(role: String): Fieldset =
      fieldset copy (role = Some(role))

    def withAttribute(attribute: (String, String)): Fieldset =
      fieldset copy (attributes = fieldset.attributes + attribute)

    def withHtml(html: Html): Fieldset =
      fieldset copy (html = html)
  }

  object LegendViewModel {

    def apply(content: Content): Legend =
      Legend(content = content)
  }

  implicit class FluentLegend(legend: Legend) {

    def asPageHeading(size: LegendSize = LegendSize.ExtraLarge): Legend =
      legend
        .copy(isPageHeading = true)
        .withCssClass(size.toString)

    def withCssClass(newClass: String): Legend =
      legend copy (classes = s"\${legend.classes} \$newClass")
  }
}
