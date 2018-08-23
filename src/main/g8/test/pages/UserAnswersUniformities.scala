package pages

import org.scalactic.Uniformity
import play.api.libs.json.{Format, Json}
import utils.UserAnswers

trait UserAnswersUniformities {

  def setTo[A](page: QuestionPage[A], value: A)(implicit format: Format[A]) = new Uniformity[UserAnswers] {
    override def normalizedOrSame(b: Any): Any =
      b match {
        case ua: UserAnswers => normalized(ua)
        case _               => b
      }

    override def normalizedCanHandle(b: Any): Boolean =
      b.isInstanceOf[UserAnswers]

    override def normalized(a: UserAnswers): UserAnswers =
      UserAnswers(a.cacheMap copy (data = a.cacheMap.data + (page.toString -> Json.toJson(value))))
  }

  def strippedOf(page: Page) = new Uniformity[UserAnswers] {
    override def normalizedOrSame(b: Any): Any =
      b match {
        case ua: UserAnswers => normalized(ua)
        case _                => b
      }

    override def normalizedCanHandle(b: Any): Boolean =
      b.isInstanceOf[UserAnswers]

    override def normalized(a: UserAnswers): UserAnswers =
      UserAnswers(a.cacheMap copy (data = a.cacheMap.data - page))
  }
}
