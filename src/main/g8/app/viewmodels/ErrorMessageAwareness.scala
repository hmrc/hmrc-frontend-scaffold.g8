package viewmodels

import play.api.data.Field
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage

trait ErrorMessageAwareness {

  def errorMessage(field: Field)(implicit messages: Messages): Option[ErrorMessage] =
    field.error
      .map {
        err =>
          ErrorMessage(
            content = Text(messages(err.message, err.args: _*)),
            visuallyHiddenText = Some(messages("error.prefix"))
          )
      }
}
