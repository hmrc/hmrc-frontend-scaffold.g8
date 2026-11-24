package controllers.auth

import config.FrontendAppConfig
import controllers.{AppController, AppControllerComponents}
import play.api.mvc.{Action, AnyContent}
import repositories.SessionRepository

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class AuthController @Inject()(
                                val controllerComponents: AppControllerComponents,
                                config: FrontendAppConfig,
                                sessionRepository: SessionRepository
                              )(implicit ec: ExecutionContext) extends AppController {

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
