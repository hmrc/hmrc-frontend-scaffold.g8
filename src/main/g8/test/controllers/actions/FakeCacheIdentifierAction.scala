package controllers.actions

import play.api.mvc.{Request, Result}
import models.requests.CacheIdentifierRequest

import scala.concurrent.Future

object FakeCacheIdentifierAction extends CacheIdentifierAction {
  override def invokeBlock[A](request: Request[A], block: (CacheIdentifierRequest[A]) => Future[Result]): Future[Result] =
    block(CacheIdentifierRequest(request, "id"))
}

