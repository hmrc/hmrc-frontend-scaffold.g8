package models

import uk.gov.hmrc.http.cache.client.CacheMap
import pages._
import play.api.libs.json._

case class UserAnswers(cacheMap: CacheMap) extends Enumerable.Implicits {

  def get[A](page: QuestionPage[A])(implicit rds: Reads[A]): Option[A] =
    cacheMap.getEntry[A](page)

  def set[A](page: QuestionPage[A], value: A)(implicit writes: Writes[A]): UserAnswers = {
    val updatedAnswers = UserAnswers(cacheMap copy (data = cacheMap.data + (page.toString -> Json.toJson(value))))

    page.cleanup(Some(value), updatedAnswers)
  }

  def remove[A](page: QuestionPage[A]): UserAnswers = {
    val updatedAnswers = UserAnswers(cacheMap copy (data = cacheMap.data - page))

    page.cleanup(None, updatedAnswers)
  }
}

object UserAnswers {

  def apply(cacheId: String): UserAnswers =
    UserAnswers(new CacheMap(cacheId, Map()))
}
