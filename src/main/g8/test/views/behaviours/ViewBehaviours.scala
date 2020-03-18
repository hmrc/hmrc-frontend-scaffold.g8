package views.behaviours

import play.twirl.api.HtmlFormat
import views.ViewSpecBase

trait ViewBehaviours extends ViewSpecBase {

  def normalPage(view: HtmlFormat.Appendable,
                 messageKeyPrefix: String,
                 expectedGuidanceKeys: String*): Unit = {

    "behave like a normal page" when {

      "rendered" must {

        "have the correct banner title" in {

          val doc = asDocument(view)
          val bannerTitle = doc.getElementsByClass("govuk-header__link govuk-header__link--service-name")
          bannerTitle.html() must equal(messages("service.name"))
        }

        "display the correct browser title" in {

          val doc = asDocument(view)
          val title = messages(s"\$messageKeyPrefix.title")
          val serviceName = messages("service.name")
          assertEqualsValue(doc, "title", s"\${title} – \${serviceName}")
        }

        "display the correct page title" in {

          val doc = asDocument(view)
          assertPageTitleEqualsMessage(doc, s"\$messageKeyPrefix.heading")
        }

        "display the correct guidance" in {

          val doc = asDocument(view)
          for (key <- expectedGuidanceKeys) assertContainsText(doc, messages(s"\$messageKeyPrefix.\$key"))
        }

        "display language toggles" in {

          val doc = asDocument(view)
          assertRenderedById(doc, "cymraeg-switch")
        }
      }
    }
  }

  def pageWithBackLink(view: HtmlFormat.Appendable): Unit = {

    "behave like a page with a back link" must {

      "have a back link" in {

        val doc = asDocument(view)
        assertRenderedById(doc, "back-link")
      }
    }
  }
}
