package navigation

import base.SpecBase
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import controllers.routes
import pages._
import models._

class NavigatorSpec extends SpecBase with MockitoSugar {

  val navigator = new Navigator

  "Navigator" when {

    "in Normal mode" must {

      "go to Index from a page that doesn't exist in the route map" in {

        case object UnknownPage extends Page
        navigator.nextPage(UnknownPage, NormalMode)(mock[UserAnswers]) mustBe routes.IndexController.onPageLoad()
      }
    }

    "in Check mode" must {

      "go to CheckYourAnswers from a page that doesn't exist in the edit route map" in {

        case object UnknownPage extends Page
        navigator.nextPage(UnknownPage, CheckMode)(mock[UserAnswers]) mustBe routes.CheckYourAnswersController.onPageLoad()
      }
    }
  }
}
