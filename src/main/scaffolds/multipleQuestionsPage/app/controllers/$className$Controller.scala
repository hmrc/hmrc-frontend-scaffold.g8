package controllers

import controllers.actions._
import forms.$className$FormProvider
import javax.inject.Inject
import models.Mode
import navigation.Navigator
import pages.$className$Page
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import repositories.SessionRepository
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController
import views.html.$className$View

import scala.concurrent.{ExecutionContext, Future}

class $className$Controller @Inject()(
                                      override val messagesApi: MessagesApi,
                                      sessionRepository: SessionRepository,
                                      navigator: Navigator,
                                      identify: IdentifierAction,
                                      getData: DataRetrievalAction,
                                      requireData: DataRequiredAction,
                                      formProvider: $className$FormProvider,
                                      val controllerComponents: MessagesControllerComponents,
                                      view: $className$View
                                     )(implicit ec: ExecutionContext) extends FrontendBaseController with I18nSupport {

  val form = formProvider()

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData) {
    implicit request =>

      val preparedForm = request.userAnswers.get($className$Page) match {
        case None => form
        case Some(value) => form.fill(value)
      }

      Ok(view(preparedForm, mode))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async {
    implicit request =>

      form.bindFromRequest().fold(
        formWithErrors =>
          Future.successful(BadRequest(view(formWithErrors, mode))),

        value =>
          for {
            updatedAnswers <- Future.fromTry(request.userAnswers.set($className$Page, value))
            _              <- sessionRepository.set(updatedAnswers)
          } yield Redirect(navigator.nextPage($className$Page, mode, updatedAnswers))
      )
  }
}
