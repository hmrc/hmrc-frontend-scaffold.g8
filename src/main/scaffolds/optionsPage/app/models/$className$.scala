package models

import utils.{WithName, Enumerable}

sealed trait $className$

object $className$ extends Enumerable[$className$] {

  case object Option1 extends WithName("option1") with $className$
  case object Option2 extends WithName("option2") with $className$

  lazy val values: Set[$className$] = Set(
    Option1, Option2
  )
}
