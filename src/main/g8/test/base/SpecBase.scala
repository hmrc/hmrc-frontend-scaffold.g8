package base

import controllers.AppControllerComponents
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
  protected given ExecutionContext = baseControllerComponents.executionContext

  protected val baseControllerComponents: MessagesControllerComponents = play.api.test.Helpers.stubMessagesControllerComponents()

  val userAnswersId: String = "id"

  def emptyUserAnswers : UserAnswers = UserAnswers(userAnswersId)

  protected def mockAppControllerComponents(userAnswers: Option[UserAnswers] = None) =
    new AppControllerComponents(
      baseControllerComponents,
      new DataRequiredActionImpl,
      new FakeDataRetrievalAction(userAnswers),
      new FakeIdentifierAction(baseControllerComponents.parsers)
    )
}
