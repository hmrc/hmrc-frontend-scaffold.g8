package models.requests

import play.api.mvc.{Request, WrappedRequest}
import utils.UserAnswers

case class OptionalDataRequest[A] (request: Request[A], externalId: String, userAnswers: Option[UserAnswers]) extends WrappedRequest[A](request)

case class DataRequest[A] (request: Request[A], externalId: String, userAnswers: UserAnswers) extends WrappedRequest[A](request)
