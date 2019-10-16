package handlers

import javax.inject.{Inject, Singleton}
import play.api.http.HeaderNames.CACHE_CONTROL
import play.api.http.HttpErrorHandler
import play.api.http.Status._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.Results._
import play.api.mvc.{RequestHeader, Result, Results}
import play.api.{Logger, PlayException}
import renderer.Renderer
import uk.gov.hmrc.play.bootstrap.http.ApplicationException

import scala.concurrent.{ExecutionContext, Future}

// NOTE: There should be changes to bootstrap to make this easier, the API in bootstrap should allow a `Future[Html]` rather than just an `Html`
@Singleton
class ErrorHandler @Inject()(
    renderer: Renderer,
    val messagesApi: MessagesApi
)(implicit ec: ExecutionContext) extends HttpErrorHandler with I18nSupport {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String = ""): Future[Result] = {

    implicit val rh: RequestHeader = request

    statusCode match {
      case BAD_REQUEST =>
        renderer.render("badRequest.njk").map(BadRequest(_))
      case NOT_FOUND   =>
        renderer.render("notFound.njk", Json.obj()).map(NotFound(_))
      case _           =>
        renderer.render("error.njk", Json.obj()).map {
          content =>
            Results.Status(statusCode)(content)
        }
    }
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {

    implicit val rh: RequestHeader = request

    logError(request, exception)
    exception match {
      case ApplicationException(result, _) =>
        Future.successful(result)
      case _ =>
        renderer.render("internalServerError.njk").map {
          content =>
            InternalServerError(content).withHeaders(CACHE_CONTROL -> "no-cache")
        }
    }
  }

  private def logError(request: RequestHeader, ex: Throwable): Unit =
    Logger.error(
      """
        |
        |! %sInternal server error, for (%s) [%s] ->
        | """.stripMargin.format(ex match {
        case p: PlayException => "@" + p.id + " - "
        case _                => ""
      }, request.method, request.uri),
      ex
    )
}
