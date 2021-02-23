package controllers

import controllers.actions.IdentifierAction
import play.api.Logging
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.play.bootstrap.binders.RedirectUrl._
import uk.gov.hmrc.play.bootstrap.binders._
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController
import views.html.{JourneyRecoveryContinueView, JourneyRecoveryStartAgainView}

import javax.inject.Inject

class JourneyRecoveryController @Inject()(
                                           val controllerComponents: MessagesControllerComponents,
                                           identify: IdentifierAction,
                                           continueView: JourneyRecoveryContinueView,
                                           startAgainView: JourneyRecoveryStartAgainView
                                         ) extends FrontendBaseController with I18nSupport with Logging {

  def onPageLoad(continueUrl: Option[RedirectUrl] = None): Action[AnyContent] = identify {
    implicit request =>

      val safeUrl: Option[String] = continueUrl.flatMap {
        unsafeUrl =>
          unsafeUrl.getEither(OnlyRelative) match {
            case Right(safeUrl) =>
              Some(safeUrl.url)
            case Left(message) =>
              logger.info(message)
              None
          }
      }

      safeUrl
        .map(url => Ok(continueView(url)))
        .getOrElse(Ok(startAgainView()))
  }
}
