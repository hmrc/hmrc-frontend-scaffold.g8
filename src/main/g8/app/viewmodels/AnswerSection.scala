package viewmodels

case class AnswerSection(headingKey: Option[String], rows: Seq[AnswerRow]) extends Section
