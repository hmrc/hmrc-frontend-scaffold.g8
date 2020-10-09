package forms.behaviours

import forms.FormSpec
import play.api.data.{Form, FormError}

trait CheckboxFieldBehaviours extends FormSpec {

  def checkboxField[T](form: Form[_],
                       fieldName: String,
                       validValues: Seq[T],
                       invalidError: FormError): Unit = {
    for {
      (value, i) <- validValues.zipWithIndex
    } yield s"binds `\$value` successfully" in {
      val data = Map(
        s"\$fieldName[\$i]" -> value.toString
      )
      form.bind(data).get mustEqual Set(value)
    }

    "fail to bind when the answer is invalid" in {
      val data = Map(
        s"\$fieldName[0]" -> "invalid value"
      )
      form.bind(data).errors must contain(invalidError)
    }
  }

  def mandatoryCheckboxField(form: Form[_],
                             fieldName: String,
                             requiredKey: String): Unit = {

    "fail to bind when no answers are selected" in {
      val data = Map.empty[String, String]
      form.bind(data).errors must contain(FormError(s"\$fieldName", requiredKey))
    }

    "fail to bind when blank answer provided" in {
      val data = Map(
        s"\$fieldName[0]" -> ""
      )
      form.bind(data).errors must contain(FormError(s"\$fieldName[0]", requiredKey))
    }
  }
}
