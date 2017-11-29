package models

import play.api.mvc.JavascriptLiteral

sealed trait Mode

case object CheckMode extends Mode
case object NormalMode extends Mode

object Mode {

  implicit val jsLiteral: JavascriptLiteral[Mode] = new JavascriptLiteral[Mode] {
    override def to(value: Mode): String = value match {
      case NormalMode => "NormalMode"
      case CheckMode => "CheckMode"
    }
  }
}
