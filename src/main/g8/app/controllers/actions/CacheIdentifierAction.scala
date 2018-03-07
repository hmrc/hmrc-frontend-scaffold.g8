package controllers.actions

import com.google.inject.Inject
import play.api.mvc.{ActionBuilder, ActionFunction, Request, Result}
import play.api.mvc.Results._
import uk.gov.hmrc.auth.core._
import uk.gov.hmrc.auth.core.retrieve.Retrievals
import config.FrontendAppConfig
import controllers.routes
import models.requests.CacheIdentifierRequest
import uk.gov.hmrc.http.UnauthorizedException
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.HeaderCarrierConverter

import scala.concurrent.{ExecutionContext, Future}

class AuthActionImpl @Inject()(override val authConnector: AuthConnector, config: FrontendAppConfig)
                              (implicit ec: ExecutionContext) extends CacheIdentifierAction with AuthorisedFunctions {

  override def invokeBlock[A](request: Request[A], block: (CacheIdentifierRequest[A]) => Future[Result]): Future[Result] = {
    implicit val hc: HeaderCarrier = HeaderCarrierConverter.fromHeadersAndSession(request.headers, Some(request.session))

    authorised().retrieve(Retrievals.internalId) {
      _.map {
        internalId => block(CacheIdentifierRequest(request, internalId))
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

trait CacheIdentifierAction extends ActionBuilder[CacheIdentifierRequest] with ActionFunction[Request, CacheIdentifierRequest]

class SessionActionImpl @Inject()(config: FrontendAppConfig)
                                 (implicit ec: ExecutionContext) extends CacheIdentifierAction {

  override def invokeBlock[A](request: Request[A], block: (CacheIdentifierRequest[A]) => Future[Result]): Future[Result] = {
    implicit val hc: HeaderCarrier = HeaderCarrierConverter.fromHeadersAndSession(request.headers, Some(request.session))

    hc.sessionId match {
      case Some(session) => block(CacheIdentifierRequest(request, session.value))
      case None => Future.successful(Redirect(routes.SessionExpiredController.onPageLoad()))
    }
  }
}