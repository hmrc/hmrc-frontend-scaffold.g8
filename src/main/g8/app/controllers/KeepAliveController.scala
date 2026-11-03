package controllers

import play.api.mvc.{Action, AnyContent}
import repositories.SessionRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class KeepAliveController @Inject()(
                                     val controllerComponents: AppControllerComponents,
                                     sessionRepository: SessionRepository
                                   )(implicit ec: ExecutionContext) extends AppController {

  def keepAlive(): Action[AnyContent] = (identify andThen getData).async {
    implicit request =>
      request.userAnswers
        .map {
          answers =>
            sessionRepository.keepAlive(answers.id).map(_ => Ok)
        }
        .getOrElse(Future.successful(Ok))
  }
}
