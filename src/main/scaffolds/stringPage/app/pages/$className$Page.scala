package pages

import controllers.routes
import play.api.mvc.Call
import utils.UserAnswers

case object $className$Page extends QuestionPage[String] with DefaultCheckModeRouting[String] {
  
  override def toString: String = "$className;format="decap"$"

  override def normalModeRoute(answers: UserAnswers): Call =
    routes.IndexController.onPageLoad()
}
