package controllers

import javax.inject.Inject

import play.api.i18n.{I18nSupport, MessagesApi}
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import controllers.actions._
import config.FrontendAppConfig
import views.html.$className;format="decap"$

import scala.concurrent.Future

class $className;format="cap"$Controller @Inject()(appConfig: FrontendAppConfig,
                                         override val messagesApi: MessagesApi,
                                         authenticate: AuthAction,
                                         getData: DataRetrievalAction,
                                         requireData: DataRequiredAction) extends FrontendController with I18nSupport {

  def onPageLoad = (authenticate andThen getData andThen requireData) {
    implicit request =>
      Ok($className;format="decap"$(appConfig))
  }
}
