package viewmodels.govuk

import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint

object hint extends HintFluency

trait HintFluency {

  object HintViewModel {

    def apply(content: Content): Hint =
      Hint(content = content)
  }

  implicit class FluentHint(hint: Hint) {

    def withId(id: String): Hint =
      hint copy (id = Some(id))

    def withCssClass(newClass: String): Hint =
      hint copy (classes = s"\${hint.classes} \$newClass")

    def withAttribute(attribute: (String, String)): Hint =
      hint copy (attributes = hint.attributes + attribute)
  }
}
