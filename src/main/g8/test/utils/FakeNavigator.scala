package utils

import play.api.mvc.Call
import pages._
import models.{Mode, NormalMode}

class FakeNavigator(desiredRoute: Call, mode: Mode = NormalMode) extends Navigator {
  override def nextPage(page: Page, mode: Mode): UserAnswers => Call = _ => desiredRoute
}
