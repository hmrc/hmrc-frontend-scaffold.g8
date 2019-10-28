package viewmodels

import play.twirl.api.Html

case class AnswerRow(label: Html, answer: Html, changeUrl: String)
