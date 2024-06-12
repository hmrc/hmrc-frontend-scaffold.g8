package controllers.auth

import javax.inject.Inject
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController
import views.html.auth.SignedOutView

class SignedOutController @Inject()(
                                     val controllerComponents: MessagesControllerComponents,
                                     view: SignedOutView
                                   ) extends FrontendBaseController with I18nSupport {

  def onPageLoad(): Action[AnyContent] = Action { implicit request =>
    Ok(view())
  }
}
