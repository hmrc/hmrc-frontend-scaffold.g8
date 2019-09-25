package views.behaviours

import play.api.data.{Form, FormError}
import play.twirl.api.HtmlFormat

trait QuestionViewBehaviours[A] extends ViewBehaviours {

  val errorKey = "value"
  val errorMessage = "error.number"
  val error = FormError(errorKey, errorMessage)

  val form: Form[A]

  def pageWithTextFields(form: Form[A],
                         createView: Form[A] => HtmlFormat.Appendable,
                         messageKeyPrefix: String,
                         expectedFormAction: String,
                         fields: String*) = {

    "behave like a question page" when {

      "rendered" must {

        for (field <- fields) {

          s"contain an input for \$field" in {
            val doc = asDocument(createView(form))
            assertRenderedById(doc, field)
          }
        }

        "not render an error summary" in {

          val doc = asDocument(createView(form))
          assertNotRenderedById(doc, "error-summary-heading")
        }
      }

      "rendered with any error" must {

        "show an error prefix in the browser title" in {

          val doc = asDocument(createView(form.withError(error)))
          assertEqualsValue(doc, "title", s"""\${messages("error.browser.title.prefix")} \${messages(s"\$messageKeyPrefix.title")}""")
        }
      }

      for (field <- fields) {

        s"rendered with an error with field '\$field'" must {

          "show an error summary" in {

            val doc = asDocument(createView(form.withError(FormError(field, "error"))))
            assertRenderedById(doc, "error-summary-heading")
          }

          s"show an error in the label for field '\$field'" in {

            val doc = asDocument(createView(form.withError(FormError(field, "error"))))
            val errorSpan = doc.getElementsByClass("error-message").first
            errorSpan.parent.attr("for") mustBe field
          }
        }
      }
    }
  }
}
