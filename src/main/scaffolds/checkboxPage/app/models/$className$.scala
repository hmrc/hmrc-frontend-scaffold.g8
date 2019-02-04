package models

import viewmodels.RadioCheckboxOption

sealed trait $className$

object $className$ extends Enumerable.Implicits {

  case object $option1key;format="Camel"$ extends WithName("$option1key;format="decap"$") with $className$
  case object $option2key;format="Camel"$ extends WithName("$option2key;format="decap"$") with $className$

  val values: Set[$className$] = Set(
    $option1key;format="Camel"$, $option2key;format="Camel"$
  )

  val options: Set[RadioCheckboxOption] = values.map {
    value =>
      RadioCheckboxOption("$className;format="decap"$", value.toString)
  }

  implicit val enumerable: Enumerable[$className$] =
    Enumerable(values.toSeq.map(v => v.toString -> v): _*)
}
