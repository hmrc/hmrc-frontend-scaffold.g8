package views.behaviours

import play.api.data.{Form, FormError}
import play.twirl.api.HtmlFormat

trait IntViewBehaviours extends QuestionViewBehaviours[Int] {
  
  val number = 123

  def intPage(createView: (Form[Int]) => HtmlFormat.Appendable,
              messageKeyPrefix: String,
              expectedFormAction: String) = {

    "behave like a page with an integer value field" when {
      "rendered" must {

        "contain a label for the value" in {
          val doc = asDocument(createView(form))
          assertContainsLabel(doc, "value", messages(s"\$messageKeyPrefix.title"))
        }

        "contain an input for the value" in {
          val doc = asDocument(createView(form))
          assertRenderedById(doc, "value")
        }
      }

      "rendered with a valid form" must {
        "include the form's value in the value input" in {
          val doc = asDocument(createView(form.fill(number)))
          doc.getElementById("value").attr("value") mustBe number.toString
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
      }
    }
  }
}
