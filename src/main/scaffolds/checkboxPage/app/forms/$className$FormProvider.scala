package forms

import javax.inject.Inject

import forms.mappings.Mappings
import play.api.data.Form
import play.api.data.Forms.set
import models.$className$

class $className$FormProvider @Inject() extends Mappings {

  def apply(): Form[Set[$className$]] =
    Form(
      "value" -> set(enumerable[$className$]("$className;format="decap"$.error.required")).verifying(nonEmptySet("$className;format="decap"$.error.required"))
    )
}
