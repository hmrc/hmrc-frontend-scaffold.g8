package utils

import play.api.mvc.Call
import identifiers.Identifier
import models.{Mode, NormalMode}

class FakeNavigator(desiredRoute: Call, mode: Mode = NormalMode) extends Navigator {
  override def nextPage(controllerId: Identifier, mode: Mode): (UserAnswers) => Call = (ua) => desiredRoute
}
