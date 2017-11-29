package controllers

import com.google.inject.Inject
import play.api.Configuration
import play.api.i18n.{I18nSupport, Lang, MessagesApi}
import play.api.mvc.{Action, AnyContent, Call, Controller}
import config.FrontendAppConfig
import uk.gov.hmrc.play.language.LanguageUtils

// TODO, upstream this into play-language
class LanguageSwitchController @Inject() (
                                           configuration: Configuration,
                                           appConfig: FrontendAppConfig,
                                           implicit val messagesApi: MessagesApi
                                         ) extends Controller with I18nSupport {

  private def langToCall(lang: String): (String) => Call = appConfig.routeToSwitchLanguage

  private def fallbackURL: String = routes.IndexController.onPageLoad().url

  private def languageMap: Map[String, Lang] = appConfig.languageMap

  def switchToLanguage(language: String): Action[AnyContent] = Action {
    implicit request =>
      val enabled = isWelshEnabled
      val lang = if (enabled) {
        languageMap.getOrElse(language, LanguageUtils.getCurrentLang)
      } else {
        Lang("en")
      }
      val redirectURL = request.headers.get(REFERER).getOrElse(fallbackURL)
      Redirect(redirectURL).withLang(Lang.apply(lang.code)).flashing(LanguageUtils.FlashWithSwitchIndicator)
  }

  private def isWelshEnabled: Boolean =
    configuration.getBoolean("microservice.services.features.welsh-translation").getOrElse(true)
}
