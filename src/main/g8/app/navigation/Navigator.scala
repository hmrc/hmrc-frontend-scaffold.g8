package navigation

import javax.inject.{Inject, Singleton}

import play.api.mvc.Call
import controllers.routes
import pages._
import models._

@Singleton
class Navigator @Inject()() {

  private val routeMap: Map[Page, UserAnswers => Call] = Map(

  )

  private val checkRouteMap: Map[Page, UserAnswers => Call] = Map(

  )

  def nextPage(page: Page, mode: Mode): UserAnswers => Call = mode match {
    case NormalMode =>
      routeMap.getOrElse(page, _ => routes.IndexController.onPageLoad())
    case CheckMode =>
      checkRouteMap.getOrElse(page, _ => routes.CheckYourAnswersController.onPageLoad())
  }
}
