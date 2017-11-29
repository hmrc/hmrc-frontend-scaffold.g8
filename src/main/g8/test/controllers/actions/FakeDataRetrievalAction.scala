package controllers.actions

import uk.gov.hmrc.http.cache.client.CacheMap
import models.requests.{AuthenticatedRequest, OptionalDataRequest}
import utils.UserAnswers

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

class FakeDataRetrievalAction(cacheMapToReturn: Option[CacheMap]) extends DataRetrievalAction {
  override protected def transform[A](request: AuthenticatedRequest[A]): Future[OptionalDataRequest[A]] = cacheMapToReturn match {
    case None => Future(OptionalDataRequest(request.request, request.externalId, None))
    case Some(cacheMap)=> Future(OptionalDataRequest(request.request, request.externalId, Some(new UserAnswers(cacheMap))))
  }
}