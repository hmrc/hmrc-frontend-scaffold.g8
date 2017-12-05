package forms

import forms.behaviours.FormBehaviours
import models.{Field, $className$, Required}

class $className$FormProviderSpec extends FormBehaviours {

  val validData: Map[String, String] = Map(
    "field1" -> "value 1",
    "field2" -> "value 2"
  )

  val form = new $className$FormProvider()()

  "$className$ form" must {
    behave like questionForm($className$("value 1", "value 2"))

    behave like formWithMandatoryTextFields(
      Field("field1", Required -> "$className;format="decap"$.error.field1.required"),
      Field("field2", Required -> "$className;format="decap"$.error.field2.required")
    )
  }
}
