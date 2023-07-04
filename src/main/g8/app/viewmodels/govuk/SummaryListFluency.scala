package viewmodels.govuk

import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist._

object summarylist extends SummaryListFluency

trait SummaryListFluency {

  object SummaryListViewModel {

    def apply(rows: Seq[SummaryListRow]): SummaryList =
      SummaryList(rows = rows)
  }

  implicit class FluentSummaryList(list: SummaryList) {

    def withoutBorders(): SummaryList =
      list.copy(classes = s"\${list.classes} govuk-summary-list--no-border")

    def withCssClass(className: String): SummaryList =
      list.copy(classes = s"\${list.classes} \$className")

    def withAttribute(attribute: (String, String)): SummaryList =
      list.copy(attributes = list.attributes + attribute)
  }

  object SummaryListRowViewModel {

    def apply(
               key: Key,
               value: Value
             ): SummaryListRow =
      SummaryListRow(
        key   = key,
        value = value
      )

    def apply(
               key: Key,
               value: Value,
               actions: Seq[ActionItem]
             ): SummaryListRow =
      SummaryListRow(
        key     = key,
        value   = value,
        actions = Some(Actions(items = actions))
      )
  }

  implicit class FluentSummaryListRow(row: SummaryListRow) {

    def withCssClass(className: String): SummaryListRow =
      row.copy(classes = s"\${row.classes} \$className")
  }

  object ActionItemViewModel {

    def apply(
               content: Content,
               href: String
             ): ActionItem =
      ActionItem(
        content = content,
        href    = href
      )
  }

  implicit class FluentActionItem(actionItem: ActionItem) {

    def withVisuallyHiddenText(text: String): ActionItem =
      actionItem.copy(visuallyHiddenText = Some(text))

    def withCssClass(className: String): ActionItem =
      actionItem.copy(classes = s"\${actionItem.classes} \$className")

    def withAttribute(attribute: (String, String)): ActionItem =
      actionItem.copy(attributes = actionItem.attributes + attribute)
  }

  object KeyViewModel {

    def apply(content: Content): Key =
      Key(content = content)
  }

  implicit class FluentKey(key: Key) {

    def withCssClass(className: String): Key =
      key.copy(classes = s"\${key.classes} \$className")
  }

  object ValueViewModel {

    def apply(content: Content): Value =
      Value(content = content)
  }

  implicit class FluentValue(value: Value) {

    def withCssClass(className: String): Value =
      value.copy(classes = s"\${value.classes} \$className")
  }
}
