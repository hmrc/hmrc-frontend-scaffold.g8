package base

import controllers.actions._
import models.UserAnswers
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.{OptionValues, TryValues}
import play.api.mvc.MessagesControllerComponents

import scala.concurrent.ExecutionContext

trait SpecBase
  extends AnyFreeSpec
    with Matchers
    with TryValues
    with OptionValues
    with ScalaFutures
    with IntegrationPatience {
  protected given ExecutionContext = stubControllerComponents.executionContext

  protected val stubControllerComponents: MessagesControllerComponents = play.api.test.Helpers.stubMessagesControllerComponents()

  val dataRequiredAction = new DataRequiredActionImpl
  def dataRetrievalAction(userAnswers: Option[UserAnswers] = None) = new FakeDataRetrievalAction(userAnswers)
  val identifierAction = new FakeIdentifierAction(stubControllerComponents.parsers)

  val userAnswersId: String = "id"

  def emptyUserAnswers : UserAnswers = UserAnswers(userAnswersId)

}
