package renderer

import config.FrontendAppConfig
import javax.inject.Inject
import play.api.libs.json.{JsObject, Json, OWrites}
import play.api.mvc.RequestHeader
import play.twirl.api.Html
import uk.gov.hmrc.nunjucks.NunjucksRenderer

import scala.concurrent.Future

class Renderer @Inject()(appConfig: FrontendAppConfig, renderer: NunjucksRenderer) {

  def render(template: String)(implicit request: RequestHeader): Future[Html] =
    renderTemplate(template, Json.obj())

  def render[A](template: String, ctx: A)(implicit request: RequestHeader, writes: OWrites[A]): Future[Html] =
    renderTemplate(template, Json.toJsObject(ctx))

  def render(template: String, ctx: JsObject)(implicit request: RequestHeader): Future[Html] =
    renderTemplate(template, ctx)

  private def renderTemplate(template: String, ctx: JsObject)(implicit request: RequestHeader): Future[Html] =
    renderer.render(template, ctx ++ Json.obj("config" -> config))

  private lazy val config: JsObject = Json.obj(
    "betaFeedbackUnauthenticatedUrl" -> appConfig.betaFeedbackUnauthenticatedUrl,
    "reportAProblemPartialUrl"       -> appConfig.reportAProblemPartialUrl,
    "reportAProblemNonJSUrl"         -> appConfig.reportAProblemNonJSUrl
  )
}
