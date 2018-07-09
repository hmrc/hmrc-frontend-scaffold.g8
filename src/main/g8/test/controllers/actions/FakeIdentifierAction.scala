package controllers.actions

import play.api.mvc.{Request, Result}
import models.requests.IdentifierRequest

import scala.concurrent.Future

object FakeIdentifierAction extends IdentifierAction {
  override def invokeBlock[A](request: Request[A], block: (IdentifierRequest[A]) => Future[Result]): Future[Result] =
    block(IdentifierRequest(request, "id"))
}

