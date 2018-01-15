package forms

import javax.inject.Inject

import forms.mappings.Mappings
import play.api.data.Form
import play.api.data.Forms._
import models.$className$

class $className$FormProvider @Inject() extends Mappings {

   def apply(): Form[$className$] = Form(
     mapping(
      "field1" -> text("$className;format="decap"$.error.field1.required")
        .verifying(maxLength($field1MaxLength$, "$className;format="decap"$.error.field1.length")),
      "field2" -> text("$className;format="decap"$.error.field2.required")
        .verifying(maxLength($field2MaxLength$, "$className;format="decap"$.error.field2.length"))
    )($className$.apply)($className$.unapply)
   )
 }
