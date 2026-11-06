package controllers

import com.google.inject.{Inject, Singleton}
import controllers.actions.{DataRequiredAction, DataRetrievalAction, IdentifierAction}
import play.api.http.FileMimeTypes
import play.api.i18n.{I18nSupport, Langs, MessagesApi}
import play.api.mvc.*
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController

import scala.concurrent.ExecutionContext

abstract class AppController extends FrontendBaseController with I18nSupport {
  override val controllerComponents: AppControllerComponents

  protected def requireData: DataRequiredAction = controllerComponents.requireData
  protected def getData: DataRetrievalAction    = controllerComponents.getData
  protected def identify: IdentifierAction      = controllerComponents.identify
}

@Singleton
final class AppControllerComponents @Inject() (
  base: MessagesControllerComponents,
  val requireData: DataRequiredAction,
  val getData: DataRetrievalAction,
  val identify: IdentifierAction
) extends MessagesControllerComponents {
  val messagesActionBuilder: MessagesActionBuilder      = base.messagesActionBuilder
  val actionBuilder: ActionBuilder[Request, AnyContent] = base.actionBuilder
  val executionContext: ExecutionContext                = base.executionContext
  val fileMimeTypes: FileMimeTypes                      = base.fileMimeTypes
  val langs: Langs                                      = base.langs
  val messagesApi: MessagesApi                          = base.messagesApi
  val parsers: PlayBodyParsers                          = base.parsers
}
