package controllers

import com.google.inject.Inject
import config.FrontendAppConfig
import play.api.Configuration
import play.api.i18n.Lang
import play.api.mvc._
import uk.gov.hmrc.play.language.{LanguageController, LanguageUtils}

class LanguageSwitchController @Inject()(
                                          configuration: Configuration,
                                          appConfig: FrontendAppConfig,
                                          languageUtils: LanguageUtils,
                                          cc: ControllerComponents
                                        ) extends LanguageController(configuration, languageUtils, cc) {

  override def fallbackURL: String = routes.IndexController.onPageLoad().url

  override def languageMap: Map[String, Lang] = appConfig.languageMap
}
