package navigation

import play.api.mvc.Call
import pages._
import models.{Mode, UserAnswers}

class FakeNavigator(desiredRoute: Call) extends Navigator {
  
  override def nextPage(page: Page, mode: Mode, userAnswers: UserAnswers): Call =
    desiredRoute
}
