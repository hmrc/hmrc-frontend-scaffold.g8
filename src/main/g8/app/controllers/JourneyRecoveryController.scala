package controllers

import play.api.Logging
import play.api.mvc.{Action, AnyContent}
import uk.gov.hmrc.play.bootstrap.binders.RedirectUrl._
import uk.gov.hmrc.play.bootstrap.binders._
import views.html.{JourneyRecoveryContinueView, JourneyRecoveryStartAgainView}

import javax.inject.Inject

class JourneyRecoveryController @Inject()(
                                           val controllerComponents: AppControllerComponents,
                                           continueView: JourneyRecoveryContinueView,
                                           startAgainView: JourneyRecoveryStartAgainView
                                         ) extends AppController with Logging {

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
