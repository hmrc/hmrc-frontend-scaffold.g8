package base

import controllers.actions._
import controllers.AppControllerComponents
import models.UserAnswers
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.{OptionValues, TryValues}
import play.api.Application
import play.api.i18n.{Messages, MessagesApi}
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.MessagesControllerComponents
import play.api.test.FakeRequest

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

  def messages(app: Application): Messages = app.injector.instanceOf[MessagesApi].preferred(FakeRequest())

  protected def mockAppControllerComponents(userAnswers: Option[UserAnswers] = None) =
    new AppControllerComponents(
      baseControllerComponents,
      new DataRequiredActionImpl,
      new FakeDataRetrievalAction(userAnswers),
      new FakeIdentifierAction(baseControllerComponents.parsers)
    )

  protected def applicationBuilder(userAnswers: Option[UserAnswers] = None): GuiceApplicationBuilder =
    new GuiceApplicationBuilder()
      .overrides(
        bind[DataRequiredAction].to[DataRequiredActionImpl],
        bind[IdentifierAction].to[FakeIdentifierAction],
        bind[DataRetrievalAction].toInstance(new FakeDataRetrievalAction(userAnswers))
      )
}
