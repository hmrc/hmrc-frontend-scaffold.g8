package utils

import java.time.format.DateTimeFormatter

import controllers.routes
import models.{CheckMode, UserAnswers}
import pages._
import play.api.i18n.Messages
import play.twirl.api.{Html, HtmlFormat}
import viewmodels.AnswerRow
import CheckYourAnswersHelper._

class CheckYourAnswersHelper(userAnswers: UserAnswers)(implicit messages: Messages) {

  private def yesOrNo(answer: Boolean)(implicit messages: Messages): Html =
    if (answer) {
      HtmlFormat.escape(messages("site.yes"))
    } else {
      HtmlFormat.escape(messages("site.no"))
    }
}

object CheckYourAnswersHelper {

  private val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
}
