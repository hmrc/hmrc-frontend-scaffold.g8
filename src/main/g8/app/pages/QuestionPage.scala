package pages

import models.UserAnswers
import play.api.libs.json.JsPath

import scala.util.{Success, Try}

trait QuestionPage[A] extends Page {

  def path: JsPath

  def cleanup(value: Option[A], userAnswers: UserAnswers): Try[UserAnswers] =
    Success(userAnswers)
}
