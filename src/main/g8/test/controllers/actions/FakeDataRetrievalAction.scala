package controllers.actions

import uk.gov.hmrc.http.cache.client.CacheMap
import models.requests.{CacheIdentifierRequest, OptionalDataRequest}
import utils.UserAnswers

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

class FakeDataRetrievalAction(cacheMapToReturn: Option[CacheMap]) extends DataRetrievalAction {
  override protected def transform[A](request: CacheIdentifierRequest[A]): Future[OptionalDataRequest[A]] = cacheMapToReturn match {
    case None => Future(OptionalDataRequest(request.request, request.cacheId, None))
    case Some(cacheMap)=> Future(OptionalDataRequest(request.request, request.cacheId, Some(new UserAnswers(cacheMap))))
  }
}