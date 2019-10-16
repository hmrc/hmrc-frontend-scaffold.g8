package models

import org.scalatest.{FreeSpec, MustMatchers}

class WithNameSpec extends FreeSpec with MustMatchers {

  object Foo extends WithName("bar")

  ".toString" - {

    "must return the correct string" in {

      Foo.toString mustEqual "bar"
    }
  }
}
