package filters

import java.util.UUID

import akka.stream.Materializer
import com.google.inject.Inject
import play.api.mvc._
import play.api.mvc.request.{Cell, RequestAttrKey}
import uk.gov.hmrc.http.{SessionKeys, HeaderNames => HMRCHeaderNames}

import scala.concurrent.{ExecutionContext, Future}

class SessionIdFilter(
                       override val mat: Materializer,
                       uuid: => UUID,
                       sessionCookieBaker: SessionCookieBaker,
                       implicit val ec: ExecutionContext
                     ) extends Filter {

  @Inject
  def this(mat: Materializer, ec: ExecutionContext, sessionCookieBaker: SessionCookieBaker) {
    this(mat, UUID.randomUUID(), sessionCookieBaker, ec)
  }

  override def apply(f: RequestHeader => Future[Result])(rh: RequestHeader): Future[Result] = {

    lazy val sessionId: String = s"session-\$uuid"

    if (rh.session.get(SessionKeys.sessionId).isEmpty) {

      val headers = rh.headers.add(
        HMRCHeaderNames.xSessionId -> sessionId
      )

      val session = rh.session + (SessionKeys.sessionId -> sessionId)

      f(rh.withHeaders(headers).addAttr(RequestAttrKey.Session, Cell(session))).map {
        result =>

          val updatedSession = if (result.session(rh).get(SessionKeys.sessionId).isDefined) {
            result.session(rh)
          } else {
            result.session(rh) + (SessionKeys.sessionId -> sessionId)
          }

          result.withSession(updatedSession)
      }
    } else {
      f(rh)
    }
  }
}
