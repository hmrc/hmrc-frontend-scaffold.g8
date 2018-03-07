package models.requests

import play.api.mvc.{Request, WrappedRequest}

case class CacheIdentifierRequest[A] (request: Request[A], cacheId: String) extends WrappedRequest[A](request)
