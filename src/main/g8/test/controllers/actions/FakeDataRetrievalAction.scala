package controllers.actions

import models.requests.{IdentifierRequest, OptionalDataRequest}
import models.{UserAnswers, UserData}

import scala.concurrent.{ExecutionContext, Future}

class FakeDataRetrievalAction(dataToReturn: Option[UserData]) extends DataRetrievalAction {

  override protected def transform[A](request: IdentifierRequest[A]): Future[OptionalDataRequest[A]] =
    dataToReturn match {
      case None =>
        Future(OptionalDataRequest(request.request, request.identifier, None))
      case Some(userData) =>
        Future(OptionalDataRequest(request.request, request.identifier, Some(new UserAnswers(userData))))
    }

  override protected implicit val executionContext: ExecutionContext =
    scala.concurrent.ExecutionContext.Implicits.global
}
