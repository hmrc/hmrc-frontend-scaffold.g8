package viewmodels

import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.Key

import scala.language.implicitConversions

object implicits extends ImplicitConversions

trait ImplicitConversions {

  implicit def stringToText(string: String)(implicit messages: Messages): Text =
    Text(messages(string))

  implicit def stringToKey(string: String)(implicit messages: Messages): Key =
    Key(content = Text(messages(string)))
}
