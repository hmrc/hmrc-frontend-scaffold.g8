package models.requests

import play.api.mvc.{Request, WrappedRequest}
import models.UserAnswers

case class OptionalDataRequest[A] (request: Request[A], internalId: String, userAnswers: Option[UserAnswers]) extends WrappedRequest[A](request)

case class DataRequest[A] (request: Request[A], internalId: String, userAnswers: UserAnswers) extends WrappedRequest[A](request)
