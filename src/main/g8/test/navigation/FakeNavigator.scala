package navigation

import play.api.mvc.Call
import pages._
import models.{Mode, NormalMode, UserAnswers}

class FakeNavigator(desiredRoute: Call, mode: Mode = NormalMode) extends Navigator {
  override def nextPage(page: Page, mode: Mode): UserAnswers => Call = _ => desiredRoute
}
