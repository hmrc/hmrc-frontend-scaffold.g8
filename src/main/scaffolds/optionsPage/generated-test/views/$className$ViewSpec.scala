package views

import play.api.data.Form
import forms.$className$Form
import models.NormalMode
import models.$className$
import views.behaviours.ViewBehaviours
import views.html.$className;format="decap"$

class $className$ViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "$className;format="decap"$"

  def createView = () => $className;format="decap"$(frontendAppConfig, $className$Form(), NormalMode)(fakeRequest, messages)

  def createViewUsingForm = (form: Form[_]) => $className;format="decap"$(frontendAppConfig, form, NormalMode)(fakeRequest, messages)

  "$className$ view" must {
    behave like normalPage(createView, messageKeyPrefix)
  }

  "$className$ view" when {
    "rendered" must {
      "contain radio buttons for the value" in {
        val doc = asDocument(createViewUsingForm($className$Form()))
        for (option <- $className$Form.options) {
          assertContainsRadioButton(doc, option.id, "value", option.value, false)
        }
      }
    }

    for(option <- $className$Form.options) {
      s"rendered with a value of '\${option.value}'" must {
        s"have the '\${option.value}' radio button selected" in {
          val doc = asDocument(createViewUsingForm($className$Form().bind(Map("value" -> s"\${option.value}"))))
          assertContainsRadioButton(doc, option.id, "value", option.value, true)

          for(unselectedOption <- $className$Form.options.filterNot(o => o == option)) {
            assertContainsRadioButton(doc, unselectedOption.id, "value", unselectedOption.value, false)
          }
        }
      }
    }
  }
}
