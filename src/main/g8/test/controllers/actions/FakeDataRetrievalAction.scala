package controllers.actions

import models.UserAnswers
import models.requests.{IdentifierRequest, OptionalDataRequest}

import scala.concurrent.{ExecutionContext, Future}

class FakeDataRetrievalAction(dataToReturn: Option[UserAnswers]) extends DataRetrievalAction {

  override protected def transform[A](request: IdentifierRequest[A]): Future[OptionalDataRequest[A]] =
    dataToReturn match {
      case None =>
        Future(OptionalDataRequest(request.request, request.identifier, None))
      case Some(userAnswers) =>
        Future(OptionalDataRequest(request.request, request.identifier, Some(userAnswers)))
    }

  override protected implicit val executionContext: ExecutionContext =
    scala.concurrent.ExecutionContext.Implicits.global
}
