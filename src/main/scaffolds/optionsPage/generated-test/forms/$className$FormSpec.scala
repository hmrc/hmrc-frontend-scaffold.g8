package forms

import forms.behaviours.FormBehaviours
import models.$className$

class $className$FormSpec extends FormBehaviours {

  val validData: Map[String, String] = Map(
    "value" -> $className$Form.options.head.value
  )

  val form = $className$Form()

  "$className$ form" must {

    behave like questionForm[$className$]($className$.values.head)

    behave like formWithOptionField("value", $className$Form.options.toSeq.map(_.value): _*)
  }
}
