package models.requests

import play.api.mvc.{Request, WrappedRequest}

case class AuthenticatedRequest[A] (request: Request[A], externalId: String) extends WrappedRequest[A](request)
