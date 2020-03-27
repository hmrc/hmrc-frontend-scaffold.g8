package views

import controllers.routes
import play.api.data.{Field, Form}
import play.api.i18n.Messages
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._
import viewmodels.RadioOption

object ViewUtils {

  def errorPrefix(form: Form[_])(implicit messages: Messages): String = {
    if (form.hasErrors || form.hasGlobalErrors) messages("error.browser.title.prefix") else ""
  }

  def backLink(backLinkUrl: Option[String] = None)(implicit messages: Messages) = {
    backLinkUrl match {
      case Some(_) => BackLink(href = backLinkUrl.get, content = Text(messages("site.back")))
      case None => BackLink(attributes = Map("id"->"back-link"), href = "#", content = Text(messages("site.back")))
    }
  }

  def mapRadioOptionsToRadioItems(field: Field, trackGa: Boolean,
                                  inputs: Seq[RadioOption])(implicit messages: Messages): Seq[RadioItem] =
    inputs.map(
      a => RadioItem(
        id = Some(a.id),
        value = Some(a.value),
        checked = field.value.getOrElse("") == a.value,
        content = Text(messages(a.messageKey)),
        attributes = if(trackGa) Map[String, String]("data-journey-click" -> s"$name$:click:\${a.id}") else Map.empty
      )
    )

  def yesNoRadioItems(field: Field, trackGa: Boolean,
                      conditionalYes: Option[Html] = None,
                      conditionalNo: Option[Html] = None)(implicit messages: Messages) = {
    Seq(
      RadioItem(
        id = Some(field.id + "-yes"),
        value = Some("true"),
        checked = field.value.contains("true"),
        content = Text(messages("site.yes")),
        attributes = {
          if (trackGa) {
            Map[String, String]("data-journey-click" -> s"$name$:click:\${field.id}-yes")
          } else {
            Map.empty
          }
        },
        conditionalHtml = conditionalYes
      ),
      RadioItem(
        id = Some(field.id + "-no"),
        value = Some("false"),
        checked = field.value.contains("false"),
        content = Text(messages("site.no")),
        attributes = {
          if (trackGa) {
            Map[String, String]("data-journey-click" -> s"$name$:click:\${field.id}-no")
          } else {
            Map.empty
          }
        },
        conditionalHtml = conditionalNo
      )
    )
  }

  def mapRadioOptionsToCheckboxItems(form: Form[_], trackGa: Boolean,
                                     inputs: Seq[RadioOption])(implicit messages: Messages): Seq[CheckboxItem] =
    inputs.map(
      a => CheckboxItem(
        id = Some(a.id),
        name = Some("value[]"),
        value = a.value,
        checked = form.data.exists(_._2 == a.value),
        content = Text(messages(a.messageKey)),
        attributes = if(trackGa) Map[String, String]("data-journey-click" -> s"$name$:click:\${a.id}") else Map.empty
      )
    )
}
