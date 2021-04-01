package viewmodels.govuk

import play.api.data.Field
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.{Fieldset, Legend}
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.{RadioItem, Radios}
import viewmodels.ErrorMessageAwareness

object radios extends RadiosFluency

trait RadiosFluency {

  object RadiosViewModel extends ErrorMessageAwareness with FieldsetFluency {

    def apply(
               field: Field,
               items: Seq[RadioItem],
               legend: Legend
             )(implicit messages: Messages): Radios =
      apply(
        field    = field,
        items    = items,
        fieldset = FieldsetViewModel(legend)
      )

    def apply(
               field: Field,
               items: Seq[RadioItem],
               fieldset: Fieldset
             )(implicit messages: Messages): Radios =
      Radios(
        fieldset     = Some(fieldset),
        name         = field.name,
        items        = items map (item => item copy (checked = field.value.isDefined && field.value == item.value)),
        errorMessage = errorMessage(field)
      )

    def yesNo(
               field: Field,
               legend: Legend
             )(implicit messages: Messages): Radios =
      yesNo(
        field    = field,
        fieldset = FieldsetViewModel(legend)
      )

    def yesNo(
               field: Field,
               fieldset: Fieldset
             )(implicit messages: Messages): Radios = {

      val items = Seq(
        RadioItem(
          id      = Some(field.id),
          value   = Some("true"),
          content = Text(messages("site.yes"))
        ),
        RadioItem(
          id      = Some(s"\${field.id}-no"),
          value   = Some("false"),
          content = Text(messages("site.no"))
        )
      )

      apply(
        field    = field,
        fieldset = fieldset,
        items    = items
      ).inline()
    }
  }

  implicit class FluentRadios(radios: Radios) {

    def withHint(hint: Hint): Radios =
      radios copy (hint = Some(hint))

    def withFormGroupClasses(classes: String): Radios =
      radios copy (formGroupClasses = classes)

    def withIdPrefix(prefix: String): Radios =
      radios copy (idPrefix = Some(prefix))

    def withCssClass(newClass: String): Radios =
      radios copy (classes = s"\${radios.classes} \$newClass")

    def withAttribute(attribute: (String, String)): Radios =
      radios copy (attributes = radios.attributes + attribute)

    def inline(): Radios =
      radios.withCssClass("govuk-radios--inline")
  }
}
