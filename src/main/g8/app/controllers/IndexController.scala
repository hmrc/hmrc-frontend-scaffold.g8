package controllers

import javax.inject.Inject
import play.api.mvc.{Action, AnyContent}
import views.html.IndexView

class IndexController @Inject()(
                                 val controllerComponents: AppControllerComponents,
                                 view: IndexView
                               ) extends AppController {

  def onPageLoad(): Action[AnyContent] = identify { implicit request =>
    Ok(view())
  }
}
