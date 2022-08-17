package viewmodels.govuk

import play.api.data.Field
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.input.{Input, PrefixOrSuffix}
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import viewmodels.{ErrorMessageAwareness, InputWidth}

object input extends InputFluency

trait InputFluency {

  object InputViewModel extends ErrorMessageAwareness {

    def apply(
               field: Field,
               label: Label
             )(implicit messages: Messages): Input =
      Input(
        id           = field.id,
        name         = field.name,
        value        = field.value,
        label        = label,
        errorMessage = errorMessage(field)
      )
  }

  implicit class FluentInput(input: Input) {

    def asEmail(): Input =
      input
        .withInputType("email")
        .withAutocomplete("email")
        .withSpellcheck(on = false)

    def asNumeric(): Input =
      input
        .withInputMode("numeric")
        .withPattern("[0-9]*")

    def withId(id: String): Input =
      input copy (id = id)

    def withInputType(inputType: String): Input =
      input copy (inputType = inputType)

    def withInputMode(inputMode: String): Input =
      input copy (inputmode = Some(inputMode))

    def describedBy(value: String): Input =
      input copy (describedBy = Some(value))

    def withHint(hint: Hint): Input =
      input copy (hint = Some(hint))

    def withFormGroupClasses(classes: String): Input =
      input copy (formGroupClasses = classes)

    def withCssClass(newClass: String): Input =
      input copy (classes = s"\${input.classes} \$newClass")

    def withAutocomplete(value: String): Input =
      input copy (autocomplete = Some(value))

    def withPattern(pattern: String): Input =
      input copy (pattern = Some(pattern))

    def withAttribute(attribute: (String, String)): Input =
      input copy (attributes = input.attributes + attribute)

    def withSpellcheck(on: Boolean = true): Input =
      input copy (spellcheck = Some(on))

    def withPrefix(prefix: PrefixOrSuffix): Input =
      input copy (prefix = Some(prefix))

    def withSuffix(suffix: PrefixOrSuffix): Input =
      input copy (suffix = Some(suffix))

    def withWidth(inputWidth: InputWidth): Input =
      input.withCssClass(inputWidth.toString)
  }
}
