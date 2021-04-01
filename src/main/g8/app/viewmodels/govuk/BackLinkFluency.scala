package viewmodels.govuk

import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.Aliases.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.backlink.BackLink

object backlink extends BackLinkFluency

trait BackLinkFluency {

  object BackLinkViewModel {

    def apply(href: String)(implicit messages: Messages): BackLink =
      BackLink(
        href    = href,
        content = Text(messages("site.back"))
      )
  }

  implicit class FluentBackLink(backLink: BackLink) {

    def withCssClass(newClass:String): BackLink =
      backLink copy (classes = s"\${backLink.classes} \$newClass")

    def withAttribute(attribute: (String, String)): BackLink =
      backLink copy (attributes = backLink.attributes + attribute)
  }
}
