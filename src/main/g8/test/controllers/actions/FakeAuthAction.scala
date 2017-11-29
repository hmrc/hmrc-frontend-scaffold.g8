package controllers.actions

import play.api.mvc.{Request, Result}
import models.requests.AuthenticatedRequest

import scala.concurrent.Future

object FakeAuthAction extends AuthAction {
  override def invokeBlock[A](request: Request[A], block: (AuthenticatedRequest[A]) => Future[Result]): Future[Result] =
    block(AuthenticatedRequest(request, "id"))
}

