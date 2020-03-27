package utils

import java.time.format.DateTimeFormatter

import controllers.routes
import models.{CheckMode, UserAnswers}
import pages._
import play.api.i18n.Messages
import CheckYourAnswersHelper._
import uk.gov.hmrc.govukfrontend.views.html.components.{ActionItem, Actions, Key, SummaryListRow, Text, Value}

class CheckYourAnswersHelper(userAnswers: UserAnswers)(implicit messages: Messages) {

  private def yesOrNo(answer: Boolean)(implicit messages: Messages) =
    if (answer) {
      messages("site.yes")
    } else {
      messages("site.no")
    }
}

object CheckYourAnswersHelper {

  private val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
}
