package filters

import java.util.UUID

import akka.stream.Materializer
import com.google.inject.Inject
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatestplus.play.OneAppPerSuite
import play.api.Application
import play.api.http.{DefaultHttpFilters, HttpFilters}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.mvc.{Action, Results}
import play.api.routing.Router
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.{HeaderNames, SessionKeys}

import scala.concurrent.ExecutionContext

object SessionIdFilterSpec {

  val sessionId = "28836767-a008-46be-ac18-695ab140e705"

  class Filters @Inject() (sessionId: SessionIdFilter) extends DefaultHttpFilters(sessionId)

  class TestSessionIdFilter @Inject() (
                                        override val mat: Materializer,
                                        ec: ExecutionContext
                                      ) extends SessionIdFilter(mat, UUID.fromString(sessionId), ec)
}

class SessionIdFilterSpec extends WordSpec with MustMatchers with OneAppPerSuite {

  import SessionIdFilterSpec._

  val router: Router = {

    import play.api.routing.sird._

    Router.from {
      case GET(p"/test") => Action {
        request =>
          val fromHeader = request.headers.get(HeaderNames.xSessionId).getOrElse("")
          val fromSession = request.session.get(SessionKeys.sessionId).getOrElse("")
          Results.Ok(
            Json.obj(
              "fromHeader" -> fromHeader,
              "fromSession" -> fromSession
            )
          )
      }
      case GET(p"/test2") => Action {
        implicit request =>
          Results.Ok.addingToSession("foo" -> "bar")
      }
    }
  }

  override lazy val app: Application = {

    import play.api.inject._

    new GuiceApplicationBuilder()
      .overrides(
        bind[HttpFilters].to[Filters],
        bind[SessionIdFilter].to[TestSessionIdFilter]
      )
      .router(router)
      .build()
  }

  ".apply" must {

    "add a sessionId if one doesn't already exist" in {

      val Some(result) = route(app, FakeRequest(GET, "/test"))

      val body = contentAsJson(result)

      (body \ "fromHeader").as[String] mustEqual s"session-\$sessionId"
      (body \ "fromSession").as[String] mustEqual s"session-\$sessionId"
    }

    "not override a sessionId if one doesn't already exist" in {

      val Some(result) = route(app, FakeRequest(GET, "/test").withSession(SessionKeys.sessionId -> "foo"))

      val body = contentAsJson(result)

      (body \ "fromHeader").as[String] mustEqual ""
      (body \ "fromSession").as[String] mustEqual "foo"
    }

    "not override other session values from the response" in {

      val Some(result) = route(app, FakeRequest(GET, "/test2"))
      session(result).data must contain("foo" -> "bar")
    }

    "not override other session values from the request" in {

      val Some(result) = route(app, FakeRequest(GET, "/test").withSession("foo" -> "bar"))
      session(result).data must contain("foo" -> "bar")
    }
  }
}
