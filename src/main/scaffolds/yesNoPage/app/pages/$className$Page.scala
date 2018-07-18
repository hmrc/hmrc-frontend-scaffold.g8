package pages

import controllers.routes
import play.api.mvc.Call
import utils.UserAnswers

case object $className$Page extends QuestionPage[Boolean] with DefaultCheckModeRouting[Boolean] {
  
  override def toString: String = "$className;format="decap"$"

  override def normalModeRoute(answers: UserAnswers): Call =
    routes.IndexController.onPageLoad()
}
