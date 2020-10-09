package models

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class WithNameSpec extends AnyFreeSpec with Matchers {

  object Foo extends WithName("bar")

  ".toString" - {

    "must return the correct string" in {
      Foo.toString mustEqual "bar"
    }
  }
}
