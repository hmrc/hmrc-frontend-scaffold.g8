package viewmodels.govuk

import play.api.data.Field
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup
import uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput.{DateInput, InputItem}
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.{Fieldset, Legend}
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import viewmodels.ErrorMessageAwareness

object date extends DateFluency

trait DateFluency {

  object DateViewModel extends ErrorMessageAwareness {

    def apply(
               field: Field,
               legend: Legend
             )(implicit messages: Messages): DateInput =
      apply(
        field    = field,
        fieldset = Fieldset(legend = Some(legend))
      )

    def apply(
               field: Field,
               fieldset: Fieldset
             )(implicit messages: Messages): DateInput = {

      val errorClass = "govuk-input--error"

      val dayError         = field.error.exists(_.args.contains(messages("date.error.day")))
      val monthError       = field.error.exists(_.args.contains(messages("date.error.month")))
      val yearError        = field.error.exists(_.args.contains(messages("date.error.year")))
      val anySpecificError = dayError || monthError || yearError
      val allFieldsError   = field.error.isDefined && !anySpecificError

      val dayErrorClass   = if (dayError || allFieldsError) errorClass else ""
      val monthErrorClass = if (monthError || allFieldsError) errorClass else ""
      val yearErrorClass  = if (yearError || allFieldsError) errorClass else ""

      val items = Seq(
        InputItem(
          id      = s"\${field.id}.day",
          name    = s"\${field.name}.day",
          value   = field("day").value,
          label   = Some(messages("date.day")),
          classes = s"govuk-input--width-2 \$dayErrorClass".trim
        ),
        InputItem(
          id      = s"\${field.id}.month",
          name    = s"\${field.name}.month",
          value   = field("month").value,
          label   = Some(messages("date.month")),
          classes = s"govuk-input--width-2 \$monthErrorClass".trim
        ),
        InputItem(
          id      = s"\${field.id}.year",
          name    = s"\${field.name}.year",
          value   = field("year").value,
          label   = Some(messages("date.year")),
          classes = s"govuk-input--width-4 \$yearErrorClass".trim
        )
      )

      DateInput(
        fieldset     = Some(fieldset),
        items        = items,
        id           = field.id,
        errorMessage = errorMessage(field)
      )
    }
  }

  implicit class FluentDate(date: DateInput) {

    def withNamePrefix(prefix: String): DateInput =
      date.copy(namePrefix = Some(prefix))

    def withHint(hint: Hint): DateInput =
      date.copy(hint = Some(hint))

    def withFormGroup(formGroup: FormGroup): DateInput =
      date.copy(formGroup = formGroup)

    def withCssClass(newClass: String): DateInput =
      date.copy(classes = s"\${date.classes} \$newClass")

    def withAttribute(attribute: (String, String)): DateInput =
      date.copy(attributes = date.attributes + attribute)

    def asDateOfBirth(): DateInput =
      date.copy(
        items = date.items map {
          item =>
            val name = item.id.split('.').last
            item.copy(autocomplete = Some(s"bday-\$name"))
        })
  }
}
