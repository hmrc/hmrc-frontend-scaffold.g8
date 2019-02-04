/*
 * Copyright 2019 HM Revenue & Customs
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

package views.behaviours

import play.api.data.{Form, FormError}
import play.twirl.api.HtmlFormat
import viewmodels.RadioCheckboxOption

trait CheckboxViewBehaviours[A] extends ViewBehaviours {

  def checkboxPage(form: Form[Set[A]],
                   createView: Form[Set[A]] => HtmlFormat.Appendable,
                   messageKeyPrefix: String,
                   options: Set[RadioCheckboxOption],
                   fieldKey: String = "value",
                   legend: Option[String] = None): Unit = {

    "behave like a checkbox page" must {
      "contain a legend for the question" in {
        val doc = asDocument(createView(form))
        val legends = doc.getElementsByTag("legend")
        legends.size mustBe 1
        legends.first.text mustBe legend.getOrElse(messages(s"$messageKeyPrefix.heading"))
      }

      "contain an input for the value" in {
        val doc = asDocument(createView(form))
        for {
          (_, i) <- options.zipWithIndex
        } yield {
          assertRenderedById(doc, form(fieldKey)(s"[$i]").id)
        }
      }

      "contain a label for each input" in {
        val doc = asDocument(createView(form))
        for {
          (option, i) <- options.zipWithIndex
        } yield {
          val id = form(fieldKey)(s"[$i]").id
          doc.select(s"label[for=$id]").text mustEqual messages(option.messageKey)
        }
      }

      "have no values checked when rendered with no form" in {
        val doc = asDocument(createView(form))
        for {
          (_, i) <- options.zipWithIndex
        } yield {
          assert(!doc.getElementById(form(fieldKey)(s"[$i]").id).hasAttr("checked"))
        }
      }

      options.zipWithIndex.foreach {
        case (checkboxOption, i) =>
          s"have correct value checked when value `${checkboxOption.value}` is given" in {
            val data: Map[String, String] =
              Map(s"$fieldKey[$i]" -> checkboxOption.value)

            val doc = asDocument(createView(form.bind(data)))
            val field = form(fieldKey)(s"[$i]")

            assert(doc.getElementById(field.id).hasAttr("checked"), s"${field.id} is not checked")

            options.zipWithIndex.foreach {
              case (option, j) =>
                if (option != checkboxOption) {
                  val field = form(fieldKey)(s"[$j]")
                  assert(!doc.getElementById(field.id).hasAttr("checked"), s"${field.id} is checked")
                }
            }
          }
      }

      "not render an error summary" in {
        val doc = asDocument(createView(form))
        assertNotRenderedById(doc, "error-summary-heading")
      }


      "show error in the title" in {
        val doc = asDocument(createView(form.withError(FormError(fieldKey, "error.invalid"))))
        doc.title.contains("Error: ") mustBe true
      }

      "show an error summary" in {
        val doc = asDocument(createView(form.withError(FormError(fieldKey, "error.invalid"))))
        assertRenderedById(doc, "error-summary-heading")
      }

      "show an error in the value field's label" in {
        val doc = asDocument(createView(form.withError(FormError(fieldKey, "error.invalid"))))
        val errorSpan = doc.getElementsByClass("error-notification").first
        errorSpan.text mustBe messages("error.invalid")
      }
    }
  }
}
