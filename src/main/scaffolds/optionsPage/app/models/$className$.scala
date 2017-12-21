package models

import utils.{Enumerable, RadioOption, WithName}

sealed trait $className$

object $className$ {

  case object Option1 extends WithName("option1") with $className$
  case object Option2 extends WithName("option2") with $className$

  val values: Set[$className$] = Set(
    Option1, Option2
  )

  val options: Set[RadioOption] = values.map {
    value =>
      RadioOption("$className;format="decap"$", value.toString)
  }

  implicit val enumerable: Enumerable[$className$] =
    Enumerable(values.toSeq.map(v => v.toString -> v): _*)
}
