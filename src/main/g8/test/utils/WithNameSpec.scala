package utils

import org.scalatest.{MustMatchers, WordSpec}

class WithNameSpec extends WordSpec with MustMatchers {

  object Foo extends WithName("bar")

  ".toString" must {
    "return the correct string" in {
      Foo.toString mustEqual "bar"
    }
  }
}
