package controllers

import javax.inject.Inject

import play.api.data.Form
import play.api.i18n.{I18nSupport, MessagesApi}
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import connectors.DataCacheConnector
import controllers.actions._
import config.FrontendAppConfig
import forms.$className$FormProvider
import models.Mode
import pages.$className$Page
import navigation.Navigator
import views.html.$className;format="decap"$

import scala.concurrent.Future

class $className$Controller @Inject()(appConfig: FrontendAppConfig,
                                      override val messagesApi: MessagesApi,
                                      dataCacheConnector: DataCacheConnector,
                                      navigator: Navigator,
                                      identify: IdentifierAction,
                                      getData: DataRetrievalAction,
                                      requireData: DataRequiredAction,
                                      formProvider: $className$FormProvider
                                      ) extends FrontendController with I18nSupport {

  val form = formProvider()

  def onPageLoad(mode: Mode) = (identify andThen getData andThen requireData) {
    implicit request =>

      val preparedForm = request.userAnswers.get($className$Page) match {
        case None => form
        case Some(value) => form.fill(value)
      }

      Ok($className;format="decap"$(appConfig, preparedForm, mode))
  }

  def onSubmit(mode: Mode) = (identify andThen getData andThen requireData).async {
    implicit request =>

      form.bindFromRequest().fold(
        (formWithErrors: Form[_]) =>
          Future.successful(BadRequest($className;format="decap"$(appConfig, formWithErrors, mode))),
        (value) => {
          val updatedAnswers = request.userAnswers.set($className$Page, value)

          dataCacheConnector.save(updatedAnswers.cacheMap).map(
            _ =>
              Redirect(navigator.nextPage($className$Page, mode)(updatedAnswers))
          )
        }
      )
  }
}
