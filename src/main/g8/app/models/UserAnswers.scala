package models

import pages._
import play.api.libs.json._

case class UserAnswers(userData: UserData) extends Enumerable.Implicits {

  def get[A](page: QuestionPage[A])(implicit rds: Reads[A]): Option[A] =
    userData.getEntry[A](page)

  def set[A](page: QuestionPage[A], value: A)(implicit writes: Writes[A]): UserAnswers = {
    val updatedAnswers = UserAnswers(userData copy (data = userData.data + (page.toString -> Json.toJson(value))))

    page.cleanup(Some(value), updatedAnswers)
  }

  def remove[A](page: QuestionPage[A]): UserAnswers = {
    val updatedAnswers = UserAnswers(userData copy (data = userData.data - page))

    page.cleanup(None, updatedAnswers)
  }
}

object UserAnswers {

  def apply(cacheId: String): UserAnswers =
    UserAnswers(new UserData(cacheId, Json.obj()))
}
