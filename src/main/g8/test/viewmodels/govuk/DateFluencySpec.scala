/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package viewmodels.govuk

import forms.mappings.Mappings
import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import play.api.data.Form
import play.api.i18n.Messages
import play.api.test.Helpers.stubMessages
import viewmodels.govuk.all._

import java.time.LocalDate

class DateFluencySpec extends AnyFreeSpec with Matchers with Mappings with OptionValues {

  ".apply"- {

    implicit val messages: Messages = stubMessages()

    val fieldset = FieldsetViewModel(LegendViewModel("foo"))

    val form : Form[LocalDate] =
      Form(
        "value" -> localDate(
          invalidKey = "fieldName.error.invalid",
          allRequiredKey = "fieldName.error.required.all",
          twoRequiredKey = "fieldName.error.required.two",
          requiredKey = "fieldName.error.required"
        )
      )

    val errorClass = "govuk-input--error"

    "must highlight all fields when there is an error, but the error does not specify any individual day/month/year field" in {

      val boundForm = form.bind(Map.empty[String, String])

      val result = DateViewModel(boundForm("value"), fieldset)

      result.items.forall(_.classes.contains(errorClass)) mustEqual true
    }

    "must highlight the day field when the error is that a day is missing" in {

      val boundForm = form.bind(Map(
        "value.month" -> "1",
        "value.year" -> "2000"
      ))

      val result = DateViewModel(boundForm("value"), fieldset)

      result.items.find(_.id == "value.day").value.classes must include(errorClass)
      result.items.find(_.id == "value.month").value.classes must not include errorClass
      result.items.find(_.id == "value.year").value.classes must not include errorClass
    }

    "must highlight the day and month fields when the error is that a day and month are both missing" in {

      val boundForm = form.bind(Map(
        "value.year" -> "2000"
      ))

      val result = DateViewModel(boundForm("value"), fieldset)

      result.items.find(_.id == "value.day").value.classes must include(errorClass)
      result.items.find(_.id == "value.month").value.classes must include(errorClass)
      result.items.find(_.id == "value.year").value.classes must not include (errorClass)
    }

    "must highlight the day and year fields when the error is that a day and year are both missing" in {

      val boundForm = form.bind(Map(
        "value.month" -> "1"
      ))

      val result = DateViewModel(boundForm("value"), fieldset)

      result.items.find(_.id == "value.day").value.classes must include(errorClass)
      result.items.find(_.id == "value.month").value.classes must not include errorClass
      result.items.find(_.id == "value.year").value.classes must include(errorClass)
    }

    "must highlight the month field when the error is that a month is missing" in {

      val boundForm = form.bind(Map(
        "value.day" -> "1",
        "value.year" -> "2000"
      ))

      val result = DateViewModel(boundForm("value"), fieldset)

      result.items.find(_.id == "value.day").value.classes must not include errorClass
      result.items.find(_.id == "value.month").value.classes must include(errorClass)
      result.items.find(_.id == "value.year").value.classes must not include errorClass
    }

    "must highlight the month and year fields when the error is that a month and year are both missing" in {

      val boundForm = form.bind(Map(
        "value.day" -> "1"
      ))

      val result = DateViewModel(boundForm("value"), fieldset)

      result.items.find(_.id == "value.day").value.classes must not include errorClass
      result.items.find(_.id == "value.month").value.classes must include(errorClass)
      result.items.find(_.id == "value.year").value.classes must include(errorClass)
    }

    "must highlight the year field when the error is that a year is missing" in {

      val boundForm = form.bind(Map(
        "value.day" -> "1",
        "value.month" -> "1"
      ))

      val result = DateViewModel(boundForm("value"), fieldset)

      result.items.find(_.id == "value.day").value.classes must not include errorClass
      result.items.find(_.id == "value.month").value.classes must not include errorClass
      result.items.find(_.id == "value.year").value.classes must include(errorClass)
    }

    "must not highlight any fields when there is not an error" in {

      val boundForm = form.bind(Map(
        "value.day" -> "1",
        "value.month" -> "1",
        "value.year" -> "2000"
      ))

      val result = DateViewModel(boundForm("value"), fieldset)

      result.items.forall(_.classes.contains(errorClass)) mustEqual false
    }
  }
}
