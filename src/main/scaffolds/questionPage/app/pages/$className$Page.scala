package pages

import controllers.routes
import models.$className$
import play.api.mvc.Call
import utils.UserAnswers

case object $className$Page extends QuestionPage[$className$] with DefaultCheckModeRouting[$className$] {
  
  override def toString: String = "$className;format="decap"$"

  override def normalModeRoute(answers: UserAnswers): Call =
    routes.IndexController.onPageLoad()
}
