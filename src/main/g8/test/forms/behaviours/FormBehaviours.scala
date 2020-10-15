package forms.behaviours

import play.api.data.Form
import forms.FormSpec
import models._

trait FormBehaviours extends FormSpec {

  val validData: Map[String, String]

  val form: Form[_]

  def questionForm[A](expectedResult: A) = {
    "bind valid values correctly" in {
      val boundForm = form.bind(validData)
      boundForm.get mustBe expectedResult
      result.errors mustBe empty
    }
  }

  def formWithOptionalTextFields(fields: String*) = {
    for (field <- fields) {
      s"bind when \$field is omitted" in {
        val data = validData - field
        val boundForm = form.bind(data)
        boundForm.errors mustBe empty
      }
    }
  }

  def formWithMandatoryTextFields(fields: Field*) = {
    for (field <- fields) {
      s"fail to bind when \${field.name} is omitted" in {
        val data = validData - field.name
        val expectedError = error(field.name, field.errorKeys(Required))
        checkForError(form, data, expectedError)
      }

      s"fail to bind when \${field.name} is blank" in {
        val data = validData + (field.name -> "")
        val expectedError = error(field.name, field.errorKeys(Required))
        checkForError(form, data, expectedError)
      }
    }
  }

  def formWithConditionallyMandatoryField(booleanField: String, field: String) = {
    s"bind when \$booleanField is false and \$field is omitted" in {
      val data = validData + (booleanField -> "false") - field
      val boundForm = form.bind(data)
      boundForm.errors mustBe empty
    }

    s"fail to bind when \$booleanField is true and \$field is omitted" in {
      val data = validData + (booleanField -> "true") - field
      val expectedError = error(field, "error.required")
      checkForError(form, data, expectedError)
    }
  }

  def formWithBooleans(fields: String*) = {
    for (field <- fields) {
      s"fail to bind when \$field is omitted" in {
        val data = validData - field
        val expectedError = error(field, "error.boolean")
        checkForError(form, data, expectedError)
      }

      s"fail to bind when \$field is invalid" in {
        val data = validData + (field -> "invalid value")
        val expectedError = error(field, "error.boolean")
        checkForError(form, data, expectedError)
      }
    }
  }

  def formWithOptionField(field: Field, validValues: String*) = {
    for (validValue <- validValues) {
      s"bind when \${field.name} is set to \$validValue" in {
        val data = validData + (field.name -> validValue)
        val boundForm = form.bind(data)
        boundForm.errors mustBe empty
      }
    }

    s"fail to bind when \${field.name} is omitted" in {
      val data = validData - field.name
      val expectedError = error(field.name, field.errorKeys(Required))
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \${field.name} is invalid" in {
      val data = validData + (field.name -> "invalid value")
      val expectedError = error(field.name, field.errorKeys(Invalid))
      checkForError(form, data, expectedError)
    }
  }

  def formWithDateField(field: String) = {
    s"fail to bind when \$field day is omitted" in {
      val data = validData - s"\$field.day"
      val expectedError = error(s"\$field.day", "error.date.day_blank")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field day is 0" in {
      val data = validData + (s"\$field.day" -> "0")
      val expectedError = error(s"\$field.day", "error.date.day_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field day is greater than 31" in {
      val data = validData + (s"\$field.day" -> "32")
      val expectedError = error(s"\$field.day", "error.date.day_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field day is negative" in {
      val data = validData + (s"\$field.day" -> "-1")
      val expectedError = error(s"\$field.day", "error.date.day_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field day is non-numeric" in {
      val data = validData + (s"\$field.day" -> "invalid")
      val expectedError = error(s"\$field.day", "error.date.day_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field month is omitted" in {
      val data = validData - s"\$field.month"
      val expectedError = error(s"\$field.month", "error.date.month_blank")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field month is 0" in {
      val data = validData + (s"\$field.month" -> "0")
      val expectedError = error(s"\$field.month", "error.date.month_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field month is greater than 12" in {
      val data = validData + (s"\$field.month" -> "13")
      val expectedError = error(s"\$field.month", "error.date.month_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field month is negative" in {
      val data = validData + (s"\$field.month" -> "-1")
      val expectedError = error(s"\$field.month", "error.date.month_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field month is non-numeric" in {
      val data = validData + (s"\$field.month" -> "invalid")
      val expectedError = error(s"\$field.month", "error.date.month_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field year is omitted" in {
      val data = validData - s"\$field.year"
      val expectedError = error(s"\$field.year", "error.date.year_blank")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field year is 0" in {
      val data = validData + (s"\$field.year" -> "0")
      val expectedError = error(s"\$field.year", "error.date.year_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field year is greater than 2050" in {
      val data = validData + (s"\$field.year" -> "2051")
      val expectedError = error(s"\$field.year", "error.date.year_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field year is negative" in {
      val data = validData + (s"\$field.year" -> "-1")
      val expectedError = error(s"\$field.year", "error.date.year_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when \$field year is non-numeric" in {
      val data = validData + (s"\$field.year" -> "invalid")
      val expectedError = error(s"\$field.year", "error.date.year_invalid")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when the \$field is invalid" in {
      val data = validData + (s"\$field.day" -> "30") + (s"\$field.month" -> "2")
      val expectedError = error("dateOfBirth", "error.invalid_date")
      checkForError(form, data, expectedError)
    }
  }
}
