package controllers

import javax.inject.Inject
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.nunjucks.{NunjucksRenderer, NunjucksSupport}
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController

import scala.concurrent.ExecutionContext

class SessionExpiredController @Inject()(
    val controllerComponents: MessagesControllerComponents,
    renderer: NunjucksRenderer
)(implicit ec: ExecutionContext) extends FrontendBaseController with I18nSupport with NunjucksSupport {

  def onPageLoad: Action[AnyContent] = Action.async {
    implicit request =>
    
      renderer.render("session-expired.njk").map(Ok(_))
  }
}
