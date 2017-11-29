package views.behaviours

import play.api.data.{Form, FormError}
import play.twirl.api.HtmlFormat

trait QuestionViewBehaviours[A] extends ViewBehaviours {
  
  val errorKey = "value"
  val errorMessage = "error.number"
  val error = FormError(errorKey, errorMessage)
  
  val form: Form[A]

  def pageWithTextFields(createView: (Form[A]) => HtmlFormat.Appendable,
                         messageKeyPrefix: String,
                         expectedFormAction: String,
                         fields: String*) = {

    "behave like a question page" when {
      "rendered" must {
        for(field <- fields) {
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

      for(field <- fields) {
        s"rendered with an error with field '\$field'" must {
          "show an error summary" in {
            val doc = asDocument(createView(form.withError(FormError(field, "error"))))
            assertRenderedById(doc, "error-summary-heading")
          }

          s"show an error in the label for field '\$field'" in {
            val doc = asDocument(createView(form.withError(FormError(field, "error"))))
            val errorSpan = doc.getElementsByClass("error-notification").first
            errorSpan.parent.attr("for") mustBe field
          }
        }
      }
    }
  }
}
