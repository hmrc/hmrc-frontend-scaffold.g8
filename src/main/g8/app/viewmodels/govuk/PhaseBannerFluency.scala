package viewmodels.govuk

import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.phasebanner.PhaseBanner

trait PhaseBannerFluency extends TagFluency {

  object PhaseBannerViewModel {

    def apply(
               phase: Phase,
               feedbackUrl: String
             )(implicit messages: Messages): PhaseBanner = {

      val link = s"""<a class="govuk-link" href="\${feedbackUrl}">\${messages("phase.feedback.link")}</a>"""
      val content = s"\${messages("phase.feedback.before")} \$link \${messages("phase.feedback.after")}"

      val tag =
        TagViewModel(Text(phase.toString))
          .withCssClass("govuk-phase-banner__content__tag")

      PhaseBanner(
        tag = Some(tag),
        content = HtmlContent(content)
      )
    }
  }
}
