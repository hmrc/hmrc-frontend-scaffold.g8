package controllers.auth

import config.FrontendAppConfig
import controllers.actions.IdentifierAction
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import repositories.SessionRepository
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController

import javax.inject.Inject
import scala.concurrent.ExecutionContext


class AuthController @Inject()(
                                val controllerComponents: MessagesControllerComponents,
                                config: FrontendAppConfig,
                                sessionRepository: SessionRepository,
                                identify: IdentifierAction
                              )(implicit ec: ExecutionContext) extends FrontendBaseController with I18nSupport {

  def signOut(): Action[AnyContent] = identify.async {
    implicit request =>
      sessionRepository
        .clear(request.userId)
        .map {
          _ =>
            Redirect(config.signOutUrl, Map("continue" -> Seq(config.exitSurveyUrl)))
      }
  }

  def signOutNoSurvey(): Action[AnyContent] = identify.async {
    implicit request =>
    sessionRepository
      .clear(request.userId)
      .map {
        _ =>
        Redirect(config.signOutUrl, Map("continue" -> Seq(routes.SignedOutController.onPageLoad().url)))
      }
  }
}
