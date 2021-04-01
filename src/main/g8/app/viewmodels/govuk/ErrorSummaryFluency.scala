package viewmodels.govuk

import play.api.data.Form
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errorsummary.{ErrorLink, ErrorSummary}

object errorsummary extends ErrorSummaryFluency

trait ErrorSummaryFluency {

  object ErrorSummaryViewModel {

    def apply(
               form: Form[_],
               errorLinkOverrides: Map[String, String] = Map.empty
             )(implicit messages: Messages): ErrorSummary = {

      val errors = form.errors.map {
        error =>
          ErrorLink(
            href    = Some(s"#\${errorLinkOverrides.getOrElse(error.key, error.key)}"),
            content = Text(messages(error.message, error.args: _*))
          )
      }

      ErrorSummary(
        errorList = errors,
        title     = Text(messages("error.summary.title"))
      )
    }
  }

  implicit class FluentErrorSummary(errorSummary: ErrorSummary) {

    def withDescription(description: Content): ErrorSummary =
      errorSummary copy (description = description)

    def withCssClass(newClass: String): ErrorSummary =
      errorSummary copy (classes = s"\${errorSummary.classes} \$newClass")

    def withAttribute(attribute: (String, String)): ErrorSummary =
      errorSummary copy (attributes = errorSummary.attributes + attribute)
  }
}
