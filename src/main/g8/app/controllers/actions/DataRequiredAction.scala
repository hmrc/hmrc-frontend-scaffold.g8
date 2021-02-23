package controllers.actions

import javax.inject.Inject
import controllers.routes
import models.requests.{DataRequest, OptionalDataRequest}
import play.api.mvc.Results.Redirect
import play.api.mvc.{ActionRefiner, Result}

import scala.concurrent.{ExecutionContext, Future}

class DataRequiredActionImpl @Inject()(implicit val executionContext: ExecutionContext) extends DataRequiredAction {

  override protected def refine[A](request: OptionalDataRequest[A]): Future[Either[Result, DataRequest[A]]] = {

    request.userAnswers match {
      case None =>
        Future.successful(Left(Redirect(routes.JourneyRecoveryController.onPageLoad())))
      case Some(data) =>
        Future.successful(Right(DataRequest(request.request, request.userId, data)))
    }
  }
}

trait DataRequiredAction extends ActionRefiner[OptionalDataRequest, DataRequest]
