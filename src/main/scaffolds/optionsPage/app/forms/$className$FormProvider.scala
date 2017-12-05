package forms

import javax.inject.Inject

import forms.mappings.Mappings
import play.api.data.Form
import models.$className$

class $className$FormProvider @Inject() extends FormErrorHelper with Mappings {

  def apply(): Form[$className$] =
    Form(
      "value" -> enumerable[$className$]("$className;format="decap"$.error.required")
    )
}
