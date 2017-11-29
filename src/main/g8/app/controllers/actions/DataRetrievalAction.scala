package controllers.actions


import com.google.inject.{ImplementedBy, Inject}
import play.api.mvc.ActionTransformer
import connectors.DataCacheConnector
import utils.UserAnswers
import models.requests.{AuthenticatedRequest, OptionalDataRequest}
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.HeaderCarrierConverter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DataRetrievalActionImpl @Inject()(val dataCacheConnector: DataCacheConnector) extends DataRetrievalAction {

  override protected def transform[A](request: AuthenticatedRequest[A]): Future[OptionalDataRequest[A]] = {
    implicit val hc = HeaderCarrierConverter.fromHeadersAndSession(request.headers, Some(request.session))

    dataCacheConnector.fetch(request.externalId).map {
      case None => OptionalDataRequest(request.request, request.externalId, None)
      case Some(data) => OptionalDataRequest(request.request, request.externalId, Some(new UserAnswers(data)))
    }
  }
}

@ImplementedBy(classOf[DataRetrievalActionImpl])
trait DataRetrievalAction extends ActionTransformer[AuthenticatedRequest, OptionalDataRequest]
