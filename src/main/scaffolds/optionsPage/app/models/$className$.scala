package models

import play.api.data.Form
import play.api.i18n.Messages
import play.api.libs.json._
import uk.gov.hmrc.viewmodels._

sealed trait $className$

object $className$ extends Enumerable.Implicits {

  case object $option1key;format="Camel"$ extends WithName("$option1key;format="decap"$") with $className$
  case object $option2key;format="Camel"$ extends WithName("$option2key;format="decap"$") with $className$

  val values: Seq[$className$] = Seq(
    $option1key;format="Camel"$,
    $option2key;format="Camel"$
  )

  def radios(form: Form[_])(implicit messages: Messages): Seq[Radios.Item] = {

    val field = form("value")
    val items = Seq(
      Radios.Radio(msg"$className;format="decap"$.$option1key;format="decap"$", $option1key;format="Camel"$.toString),
      Radios.Radio(msg"$className;format="decap"$.$option2key;format="decap"$", $option2key;format="Camel"$.toString)
    )

    Radios(field, items)
  }

  implicit val enumerable: Enumerable[$className$] =
    Enumerable(values.map(v => v.toString -> v): _*)
}
