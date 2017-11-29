package utils

import org.scalatest.{EitherValues, OptionValues, MustMatchers, WordSpec}
import play.api.data.validation.ValidationError
import play.api.libs.json._

object EnumerableSpec {

  sealed trait Foo
  case object Bar extends Foo
  case object Baz extends Foo

  object Foo extends Enumerable[Foo] {
    override def values: Set[Foo] = Set(Bar, Baz)
  }
}

class EnumerableSpec extends WordSpec with MustMatchers with EitherValues with OptionValues {

  import EnumerableSpec._

  ".reads" must {

    "be found implicitly" in {
      implicitly[Reads[Foo]]
    }

    Foo.values.foreach {
      value =>
        s"bind correctly for: \$value" in {
          Json.fromJson[Foo](JsString(value.toString)).asEither.right.value mustEqual value
        }
    }

    "fail to bind for invalid values" in {
      Json.fromJson[Foo](JsString("invalid")).asEither.left.value must contain(JsPath -> Seq(ValidationError("error.invalid")))
    }
  }

  ".writes" must {

    "be found implicitly" in {
      implicitly[Writes[Foo]]
    }

    Foo.values.foreach {
      value =>
        s"write \$value" in {
          Json.toJson(value) mustEqual JsString(value.toString)
        }
    }
  }

  ".formats" must {

    "be found implicitly" in {
      implicitly[Format[Foo]]
    }
  }

  ".withName" must {

    "be undefined when an invalid string is used" in {
      Foo.withName("invalid") mustNot be(defined)
    }

    Foo.values.foreach {
      value =>
        s"return `Some(\$value)`" in {
          Foo.withName(value.toString).value mustEqual value
        }
    }
  }
}
