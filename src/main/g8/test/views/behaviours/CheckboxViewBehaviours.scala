package views.behaviours

import play.api.data.{Form, FormError}
import play.twirl.api.HtmlFormat
import viewmodels.RadioOption

trait CheckboxViewBehaviours[A] extends ViewBehaviours {

  def checkboxPage(form: Form[Set[A]],
                   createView: Form[Set[A]] => HtmlFormat.Appendable,
                   messageKeyPrefix: String,
                   options: Seq[RadioOption],
                   fieldKey: String = "value",
                   legend: Option[String] = None): Unit = {

    "behave like a checkbox page" must {
      "contain a legend for the question" in {
        val doc = asDocument(createView(form))
        val legends = doc.getElementsByTag("legend")
        legends.size mustBe 1
        legends.text contains legend.getOrElse(messages(s"\$messageKeyPrefix.heading"))
      }

      "contain an input for the value" in {
        val doc = asDocument(createView(form))
        for {
          (_, i) <- options.zipWithIndex
        } yield {
          assertRenderedById(doc, options(i).id)
        }
      }

      "contain a label for each input" in {
        val doc = asDocument(createView(form))
        for {
          (option, i) <- options.zipWithIndex
        } yield {
          val id = options(i).id
          doc.select(s"label[for=\$id]").text mustEqual messages(option.messageKey)
        }
      }

      "have no values checked when rendered with no form" in {
        val doc = asDocument(createView(form))
        for {
          (_, i) <- options.zipWithIndex
        } yield {
          val id = options(i).id
          assert(!doc.getElementById(id).hasAttr("checked"))
        }
      }

      options.zipWithIndex.foreach {
        case (checkboxOption, i) =>
          s"have correct value checked when value `\${checkboxOption.value}` is given" in {
            val data: Map[String, String] =
              Map("value" -> checkboxOption.value)

            val doc = asDocument(createView(form.bind(data)))

            assert(doc.getElementById(checkboxOption.id).hasAttr("checked"), s"\${checkboxOption.id} is not checked")

            options.zipWithIndex.foreach {
              case (option, j) =>
                if (option != checkboxOption) {
                  assert(!doc.getElementById(option.id).hasAttr("checked"), s"\${option.id} is checked")
                }
            }
          }
      }

      "not render an error summary" in {
        val doc = asDocument(createView(form))
        assertNotRenderedByCssSelector(doc, ".govuk-error-summary")
      }

      "show error in the title" in {
        val doc = asDocument(createView(form.withError(FormError(fieldKey, "error.invalid"))))
        doc.title.contains(messages("error.browser.title.prefix")) mustBe true
      }

      "show an error summary" in {
        val doc = asDocument(createView(form.withError(FormError(fieldKey, "error.invalid"))))
        assertRenderedByCssSelector(doc, ".govuk-error-summary")
      }

      "show an error associated with the value field" in {
        val doc = asDocument(createView(form.withError(FormError(fieldKey, "error.invalid"))))
        val errorSpan = doc.getElementsByClass("govuk-error-message").first
        errorSpan.text mustBe (messages("error.browser.title.prefix") + " " + messages("error.invalid"))
        doc.getElementsByTag("fieldset").first.attr("aria-describedby") contains errorSpan.attr("id")
      }
    }
  }
}
