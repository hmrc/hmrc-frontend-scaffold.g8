package pages

import utils.UserAnswers

trait QuestionPage[A] extends Page {

  def cleanup(value: Option[A], userAnswers: UserAnswers): UserAnswers = userAnswers
}
