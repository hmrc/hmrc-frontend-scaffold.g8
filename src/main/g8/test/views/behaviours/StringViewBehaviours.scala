package views.behaviours

import play.api.data.Form
import play.twirl.api.HtmlFormat

trait StringViewBehaviours extends QuestionViewBehaviours[String] {

  val answer = "answer"

  def stringPage(form: Form[String],
                 createView: Form[String] => HtmlFormat.Appendable,
                 messageKeyPrefix: String,
                 expectedFormAction: String,
                 expectedHintKey: Option[String] = None) = {

    "behave like a page with a string value field" when {

      "rendered" must {

        "contain a label for the value" in {

          val doc = asDocument(createView(form))
          val expectedHintText = expectedHintKey map (k => messages(k))
          assertContainsLabel(doc, "value", messages(s"\$messageKeyPrefix.heading"), expectedHintText)
        }

        "contain an input for the value" in {

          val doc = asDocument(createView(form))
          assertRenderedById(doc, "value")
        }
      }

      "rendered with a valid form" must {

        "include the form's value in the value input" in {

          val doc = asDocument(createView(form.fill(answer)))
          doc.getElementById("value").attr("value") mustBe answer
        }
      }

      "rendered with an error" must {

        "show an error summary" in {

          val doc = asDocument(createView(form.withError(error)))
          assertRenderedById(doc, "error-summary-heading")
        }

        "show an error in the value field's label" in {

          val doc = asDocument(createView(form.withError(error)))
          val errorSpan = doc.getElementsByClass("error-message").first
          errorSpan.text mustBe messages(errorMessage)
        }

        "show an error prefix in the browser title" in {
          
          val doc = asDocument(createView(form.withError(error)))
          assertEqualsValue(doc, "title", s"""\${messages("error.browser.title.prefix")} \${messages(s"\$messageKeyPrefix.title")}""")
        }
      }
    }
  }
}
