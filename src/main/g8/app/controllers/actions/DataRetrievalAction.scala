package controllers.actions

import com.google.inject.Inject
import play.api.mvc.ActionTransformer
import connectors.DataCacheConnector
import models.UserAnswers
import models.requests.{IdentifierRequest, OptionalDataRequest}
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.HeaderCarrierConverter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DataRetrievalActionImpl @Inject()(val dataCacheConnector: DataCacheConnector) extends DataRetrievalAction {

  override protected def transform[A](request: IdentifierRequest[A]): Future[OptionalDataRequest[A]] = {
    implicit val hc = HeaderCarrierConverter.fromHeadersAndSession(request.headers, Some(request.session))

    dataCacheConnector.fetch(request.identifier).map {
      case None => OptionalDataRequest(request.request, request.identifier, None)
      case Some(data) => OptionalDataRequest(request.request, request.identifier, Some(UserAnswers(data)))
    }
  }
}

trait DataRetrievalAction extends ActionTransformer[IdentifierRequest, OptionalDataRequest]
