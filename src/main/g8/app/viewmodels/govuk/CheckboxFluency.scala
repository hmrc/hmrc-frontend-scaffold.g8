package viewmodels.govuk

import play.api.data.Form
import play.api.i18n.Messages
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.{CheckboxItem, Checkboxes}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.{Fieldset, Legend}
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import viewmodels.ErrorMessageAwareness

object checkbox extends CheckboxFluency

trait CheckboxFluency {

  object CheckboxesViewModel extends ErrorMessageAwareness with FieldsetFluency {

    def apply(
               form: Form[_],
               name: String,
               items: Seq[CheckboxItem],
               legend: Legend
             )(implicit messages: Messages): Checkboxes =
      apply(
        form = form,
        name = name,
        items = items,
        fieldset = FieldsetViewModel(legend)
      )

    def apply(
               form: Form[_],
               name: String,
               items: Seq[CheckboxItem],
               fieldset: Fieldset
             )(implicit messages: Messages): Checkboxes =
      Checkboxes(
        fieldset     = Some(fieldset),
        name         = name,
        errorMessage = errorMessage(form(name)),
        items        = items.map {
          item =>
            item copy (checked = form.data.exists(data => data._2 == item.value))
        }
      )
  }

  implicit class FluentCheckboxes(checkboxes: Checkboxes) {

    def describedBy(value: String): Checkboxes =
      checkboxes copy (describedBy = Some(value))
  }

  object CheckboxItemViewModel {

    def apply(
               content: Content,
               fieldId: String,
               index: Int,
               value: String
             ): CheckboxItem =
      CheckboxItem(
        content = content,
        id      = Some(s"\${fieldId}_\$index"),
        name    = Some(s"\$fieldId[\$index]"),
        value   = value
      )
  }

  implicit class FluentCheckboxItem(item: CheckboxItem) {

    def withLabel(label: Label): CheckboxItem =
      item copy (label = Some(label))

    def withHint(hint: Hint): CheckboxItem =
      item copy (hint = Some(hint))

    def withConditionalHtml(html: Html): CheckboxItem =
      item copy (conditionalHtml = Some(html))

    def disabled(): CheckboxItem =
      item copy (disabled = true)

    def withAttribute(attribute: (String, String)): CheckboxItem =
      item copy (attributes = item.attributes + attribute)
  }
}
