package viewmodels

case class RepeaterAnswerSection (headingKey: String,
                                  relevanceRow: AnswerRow,
                                  rows: Seq[RepeaterAnswerRow],
                                  addLinkKey: String,
                                  addLinkUrl: String) extends Section
