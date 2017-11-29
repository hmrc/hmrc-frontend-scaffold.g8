package utils

import play.api.data.Form

object FormHelpers {

  def getErrorByKey[A](form: Form[_], errorKey: String) = {
    form.error(errorKey) match {
      case None => ""
      case Some(error) => error.message
    }
  }
}
