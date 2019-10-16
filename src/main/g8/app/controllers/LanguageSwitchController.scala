package controllers

import com.google.inject.Inject
import config.FrontendAppConfig
import play.api.i18n.{I18nSupport, Lang, MessagesApi}
import play.api.mvc._
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController

class LanguageSwitchController @Inject()(
    appConfig: FrontendAppConfig,
    override implicit val messagesApi: MessagesApi,
    val controllerComponents: MessagesControllerComponents
) extends FrontendBaseController with I18nSupport {

  private def fallbackURL: String = routes.IndexController.onPageLoad().url

  private def languageMap: Map[String, Lang] = appConfig.languageMap

  def switchToLanguage(language: String): Action[AnyContent] = Action {
    implicit request =>

      val lang = languageMap.getOrElse(language, Lang.defaultLang)

      val redirectURL = request.headers.get(REFERER).getOrElse(fallbackURL)

      Redirect(redirectURL).withLang(lang)
  }
}