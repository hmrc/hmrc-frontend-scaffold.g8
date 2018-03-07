package controllers.actions

import com.google.inject.Inject
import play.api.mvc.ActionTransformer
import connectors.DataCacheConnector
import utils.UserAnswers
import models.requests.{CacheIdentifierRequest, OptionalDataRequest}
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.HeaderCarrierConverter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DataRetrievalActionImpl @Inject()(val dataCacheConnector: DataCacheConnector) extends DataRetrievalAction {

  override protected def transform[A](request: CacheIdentifierRequest[A]): Future[OptionalDataRequest[A]] = {
    implicit val hc = HeaderCarrierConverter.fromHeadersAndSession(request.headers, Some(request.session))

    dataCacheConnector.fetch(request.cacheId).map {
      case None => OptionalDataRequest(request.request, request.cacheId, None)
      case Some(data) => OptionalDataRequest(request.request, request.cacheId, Some(new UserAnswers(data)))
    }
  }
}

trait DataRetrievalAction extends ActionTransformer[CacheIdentifierRequest, OptionalDataRequest]
