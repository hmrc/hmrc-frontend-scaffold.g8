package controllers.actions

import com.google.inject.Inject
import play.api.mvc.{ActionBuilder, ActionFunction, Request, Result}
import play.api.mvc.Results._
import uk.gov.hmrc.auth.core._
import uk.gov.hmrc.auth.core.retrieve.Retrievals
import config.FrontendAppConfig
import controllers.routes
import models.requests.IdentifierRequest
import uk.gov.hmrc.http.UnauthorizedException
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.HeaderCarrierConverter

import scala.concurrent.{ExecutionContext, Future}

class AuthenticatedIdentifierAction @Inject()(override val authConnector: AuthConnector, config: FrontendAppConfig)
                              (implicit ec: ExecutionContext) extends IdentifierAction with AuthorisedFunctions {

  override def invokeBlock[A](request: Request[A], block: (IdentifierRequest[A]) => Future[Result]): Future[Result] = {
    implicit val hc: HeaderCarrier = HeaderCarrierConverter.fromHeadersAndSession(request.headers, Some(request.session))

    authorised().retrieve(Retrievals.internalId) {
      _.map {
        internalId => block(IdentifierRequest(request, internalId))
      }.getOrElse(throw new UnauthorizedException("Unable to retrieve internal Id"))
    } recover {
      case ex: NoActiveSession =>
        Redirect(config.loginUrl, Map("continue" -> Seq(config.loginContinueUrl)))
      case ex: InsufficientEnrolments =>
        Redirect(routes.UnauthorisedController.onPageLoad)
      case ex: InsufficientConfidenceLevel =>
        Redirect(routes.UnauthorisedController.onPageLoad)
      case ex: UnsupportedAuthProvider =>
        Redirect(routes.UnauthorisedController.onPageLoad)
      case ex: UnsupportedAffinityGroup =>
        Redirect(routes.UnauthorisedController.onPageLoad)
      case ex: UnsupportedCredentialRole =>
        Redirect(routes.UnauthorisedController.onPageLoad)
    }
  }
}

trait IdentifierAction extends ActionBuilder[IdentifierRequest] with ActionFunction[Request, IdentifierRequest]

class SessionIdentifierAction @Inject()(config: FrontendAppConfig)
                                 (implicit ec: ExecutionContext) extends IdentifierAction {

  override def invokeBlock[A](request: Request[A], block: (IdentifierRequest[A]) => Future[Result]): Future[Result] = {
    implicit val hc: HeaderCarrier = HeaderCarrierConverter.fromHeadersAndSession(request.headers, Some(request.session))

    hc.sessionId match {
      case Some(session) => block(IdentifierRequest(request, session.value))
      case None => Future.successful(Redirect(routes.SessionExpiredController.onPageLoad()))
    }
  }
}
