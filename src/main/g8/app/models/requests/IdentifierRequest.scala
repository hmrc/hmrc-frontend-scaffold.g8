package models.requests

import play.api.mvc.{Request, WrappedRequest}

case class IdentifierRequest[A] (request: Request[A], userId: String) extends WrappedRequest[A](request)
