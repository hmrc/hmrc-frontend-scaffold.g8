package utils

import javax.inject.Inject
import pages.QuestionPage
import models.Mode

class Navigator @Inject()() {
  
  def nextPage[A](page: QuestionPage[A], mode: Mode, userAnswers: UserAnswers, originalAnswer: Option[A]) =
    page.nextPage(mode, userAnswers, originalAnswer)
}
