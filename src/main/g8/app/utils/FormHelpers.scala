package utils

import play.api.data.Form
import play.api.i18n.Messages

object FormHelpers {

  def getErrorByKey[A](form: Form[_], errorKey: String) = {
    form.error(errorKey) match {
      case None => ""
      case Some(error) => error.message
    }
  }

  def errorPrefix(form: Form[_])(implicit messages: Messages): String = {
    if (form.hasErrors || form.hasGlobalErrors) messages("error.browser.title.prefix") else ""
  }
}
