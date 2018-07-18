package pages

import controllers.routes
import models.{CheckMode, Mode, NormalMode}
import play.api.mvc.Call
import utils.UserAnswers

trait QuestionPage[A] extends Page {

  def cleanup(value: Option[A], userAnswers: UserAnswers): UserAnswers = userAnswers

  def nextPage(mode: Mode, userAnswers: UserAnswers, original: Option[A]) = mode match {
    case NormalMode =>
      normalModeRoute(userAnswers)
    case CheckMode =>
      checkModeRoute(userAnswers, original)
  }

  protected def normalModeRoute(answers: UserAnswers): Call

  protected def checkModeRoute(answers: UserAnswers, data: Option[A]): Call
}

trait DefaultCheckModeRouting[A] {
  self: QuestionPage[A] =>

  override protected def checkModeRoute(answers: UserAnswers, data: Option[A]): Call =
    routes.CheckYourAnswersController.onPageLoad()
}
