package views

import play.api.data.Form
import play.api.i18n.Messages

object ViewUtils {

  def title(form: Form[_], title: String, section: Option[String] = None)(implicit messages: Messages): String =
    titleNoForm(
      title   = s"\${errorPrefix(form)} \${messages(title)}",
      section = section
    )

  def titleNoForm(title: String, section: Option[String] = None)(implicit messages: Messages): String =
    s"\${messages(title)} - \${section.fold("")(messages(_) + " - ")}\${messages("service.name")} - \${messages("site.govuk")}"

  def errorPrefix(form: Form[_])(implicit messages: Messages): String = {
    if (form.hasErrors || form.hasGlobalErrors) messages("error.prefix") else ""
  }
}
