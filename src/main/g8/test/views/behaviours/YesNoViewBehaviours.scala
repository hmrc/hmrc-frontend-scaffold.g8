package views.behaviours

import play.api.data.{Form, FormError}
import play.twirl.api.HtmlFormat

trait YesNoViewBehaviours extends QuestionViewBehaviours[Boolean] {

  def yesNoPage(createView: (Form[Boolean]) => HtmlFormat.Appendable,
                messageKeyPrefix: String,
                expectedFormAction: String) = {

    "behave like a page with a Yes/No question" when {
      "rendered" must {
        "contain a legend for the question" in {
          val doc = asDocument(createView(form))
          val legends = doc.getElementsByTag("legend")
          legends.size mustBe 1
          legends.first.text mustBe messages(s"\$messageKeyPrefix.heading")
        }

        "contain an input for the value" in {
          val doc = asDocument(createView(form))
          assertRenderedById(doc, "value-yes")
          assertRenderedById(doc, "value-no")
        }

        "have no values checked when rendered with no form" in {
          val doc = asDocument(createView(form))
          assert(!doc.getElementById("value-yes").hasAttr("checked"))
          assert(!doc.getElementById("value-no").hasAttr("checked"))
        }

        "not render an error summary" in {
          val doc = asDocument(createView(form))
          assertNotRenderedById(doc, "error-summary_header")
        }
      }

      "rendered with a value of true" must {
        behave like answeredYesNoPage(createView, true)
      }

      "rendered with a value of false" must {
        behave like answeredYesNoPage(createView, false)
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


  def answeredYesNoPage(createView: (Form[Boolean]) => HtmlFormat.Appendable, answer: Boolean) = {

    "have only the correct value checked" in {
      val doc = asDocument(createView(form.fill(answer)))
      assert(doc.getElementById("value-yes").hasAttr("checked") == answer)
      assert(doc.getElementById("value-no").hasAttr("checked") != answer)
    }

    "not render an error summary" in {
      val doc = asDocument(createView(form.fill(answer)))
      assertNotRenderedById(doc, "error-summary_header")
    }
  }
}
