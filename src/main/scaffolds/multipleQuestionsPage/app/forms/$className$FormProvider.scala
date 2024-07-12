package forms

import javax.inject.Inject

import forms.mappings.Mappings
import play.api.data.Form
import play.api.data.Forms._
import models.$className$

class $className$FormProvider @Inject() extends Mappings {

   def apply(): Form[$className$] = Form(
     mapping(
      "$field1Name$" -> text("$className;format="decap"$.error.$field1Name$.required")
        .verifying(maxLength($field1MaxLength$, "$className;format="decap"$.error.$field1Name$.length")),
      "$field2Name$" -> text("$className;format="decap"$.error.$field2Name$.required")
        .verifying(maxLength($field2MaxLength$, "$className;format="decap"$.error.$field2Name$.length"))
    )($className$.apply)(x => Some((x.$field1Name$, x.$field2Name$)))
   )
 }
