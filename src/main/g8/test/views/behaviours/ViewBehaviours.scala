package views.behaviours

import play.twirl.api.HtmlFormat
import views.ViewSpecBase

trait ViewBehaviours extends ViewSpecBase {

  def normalPage(view: () => HtmlFormat.Appendable,
                 messageKeyPrefix: String,
                 expectedGuidanceKeys: String*) = {

    "behave like a normal page" when {
      "rendered" must {
        "have the correct banner title" in {
          val doc = asDocument(view())
          val nav = doc.getElementById("proposition-menu")
          val span = nav.children.first
          span.text mustBe messagesApi("site.service_name")
        }

        "display the correct browser title" in {
          val doc = asDocument(view())
          assertEqualsMessage(doc, "title", s"\$messageKeyPrefix.title")
        }

        "display the correct page heading" in {
          val doc = asDocument(view())
          assertPageTitleEqualsMessage(doc, s"\$messageKeyPrefix.heading")
        }

        "display the correct guidance" in {
          val doc = asDocument(view())
          for (key <- expectedGuidanceKeys) assertContainsText(doc, messages(s"\$messageKeyPrefix.\$key"))
        }

        "display language toggles" in {
          val doc = asDocument(view())
          assertRenderedById(doc, "cymraeg-switch")
        }
      }
    }
  }

  def pageWithBackLink(view: () => HtmlFormat.Appendable) = {
    
    "behave like a page with a back link" must {
      "have a back link" in {
        val doc = asDocument(view())
        assertRenderedById(doc, "back-link")
      }
    }
  }
}
