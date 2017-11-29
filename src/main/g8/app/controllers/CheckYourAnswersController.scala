package controllers

import com.google.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import controllers.actions.{AuthAction, DataRequiredAction, DataRetrievalAction}
import utils.CheckYourAnswersHelper
import viewmodels.AnswerSection
import views.html.check_your_answers
import config.FrontendAppConfig
import uk.gov.hmrc.play.bootstrap.controller.FrontendController

class CheckYourAnswersController @Inject()(appConfig: FrontendAppConfig,
                                           override val messagesApi: MessagesApi,
                                           authenticate: AuthAction,
                                           getData: DataRetrievalAction,
                                           requireData: DataRequiredAction) extends FrontendController with I18nSupport {

  def onPageLoad() = (authenticate andThen getData andThen requireData) {
    implicit request =>
      val checkYourAnswersHelper = new CheckYourAnswersHelper(request.userAnswers)
      val sections = Seq(AnswerSection(None, Seq()))
      Ok(check_your_answers(appConfig, sections))
  }
}
