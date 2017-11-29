package controllers

import javax.inject.Inject

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import config.FrontendAppConfig
import views.html.session_expired

import scala.concurrent.Future

class SessionExpiredController @Inject()(val appConfig: FrontendAppConfig,
                                         val messagesApi: MessagesApi) extends FrontendController with I18nSupport {

  def onPageLoad: Action[AnyContent] = Action { implicit request =>
    Ok(session_expired(appConfig))
  }
}
