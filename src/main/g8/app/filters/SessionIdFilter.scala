package filters

import java.util.UUID

import akka.stream.Materializer
import com.google.inject.Inject
import play.api.http.HeaderNames
import play.api.mvc._
import uk.gov.hmrc.http.{SessionKeys, HeaderNames => HMRCHeaderNames}

import scala.concurrent.{ExecutionContext, Future}

class SessionIdFilter (
                        override val mat: Materializer,
                        uuid: => UUID,
                        implicit val ec: ExecutionContext
                      ) extends Filter {

  @Inject
  def this(mat: Materializer, ec: ExecutionContext) {
    this(mat, UUID.randomUUID(), ec)
  }

  override def apply(f: (RequestHeader) => Future[Result])(rh: RequestHeader): Future[Result] = {

    lazy val sessionId: String = s"session-\$uuid"

    if (rh.session.get(SessionKeys.sessionId).isEmpty) {

      val cookies: String = {

        val session: Session =
          rh.session + (SessionKeys.sessionId -> sessionId)

        val cookies =
          rh.cookies ++ Seq(Session.encodeAsCookie(session))

        Cookies.encodeCookieHeader(cookies.toSeq)
      }

      val headers = rh.headers.add(
        HMRCHeaderNames.xSessionId -> sessionId,
        HeaderNames.COOKIE -> cookies
      )

      f(rh.copy(headers = headers)).map {
        result =>

          val cookies =
            Cookies.fromSetCookieHeader(result.header.headers.get(HeaderNames.SET_COOKIE))

          val session = Session.decodeFromCookie(cookies.get(Session.COOKIE_NAME)).data
            .foldLeft(rh.session) {
              case (m, n) =>
                m + n
            }

          result.withSession(session + (SessionKeys.sessionId -> sessionId))
      }
    } else {
      f(rh)
    }
  }
}

