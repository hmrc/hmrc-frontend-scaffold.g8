package controllers.actions

import uk.gov.hmrc.http.cache.client.CacheMap
import models.requests.{IdentifierRequest, OptionalDataRequest}
import models.UserAnswers

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

class FakeDataRetrievalAction(cacheMapToReturn: Option[CacheMap]) extends DataRetrievalAction {
  override protected def transform[A](request: IdentifierRequest[A]): Future[OptionalDataRequest[A]] = cacheMapToReturn match {
    case None => Future(OptionalDataRequest(request.request, request.identifier, None))
    case Some(cacheMap)=> Future(OptionalDataRequest(request.request, request.identifier, Some(new UserAnswers(cacheMap))))
  }
}
