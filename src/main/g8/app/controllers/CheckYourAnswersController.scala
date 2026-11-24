package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, AnyContent}
import viewmodels.govuk.summarylist._
import views.html.CheckYourAnswersView

class CheckYourAnswersController @Inject()(
                                            val controllerComponents: AppControllerComponents,
                                            view: CheckYourAnswersView
                                          ) extends AppController {

  def onPageLoad(): Action[AnyContent] = (identify andThen getData andThen requireData) {
    implicit request =>

      val list = SummaryListViewModel(
        rows = Seq.empty
      )

      Ok(view(list))
  }
}
