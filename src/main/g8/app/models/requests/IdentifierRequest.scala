package models.requests

import play.api.mvc.{Request, WrappedRequest}

case class IdentifierRequest[A] (request: Request[A], identifier: String) extends WrappedRequest[A](request)
