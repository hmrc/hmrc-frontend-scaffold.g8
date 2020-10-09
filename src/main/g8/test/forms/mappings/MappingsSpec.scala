package forms.mappings

import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import play.api.data.{Form, FormError}
import models.Enumerable

object MappingsSpec {

  sealed trait Foo
  case object Bar extends Foo
  case object Baz extends Foo

  object Foo {

    val values: Set[Foo] = Set(Bar, Baz)

    implicit val fooEnumerable: Enumerable[Foo] =
      Enumerable(values.toSeq.map(v => v.toString -> v): _*)
  }
}

class MappingsSpec extends AnyFreeSpec with Matchers with OptionValues with Mappings {

  import MappingsSpec._

  "text" - {

    val testForm: Form[String] =
      Form(
        "value" -> text()
      )

    "must bind a valid string" in {
      val result = testForm.bind(Map("value" -> "foobar"))
      result.get mustEqual "foobar"
    }

    "must not bind an empty string" in {
      val result = testForm.bind(Map("value" -> ""))
      result.errors must contain(FormError("value", "error.required"))
    }

    "must not bind a string of whitespace only" in {
      val result = testForm.bind(Map("value" -> " \t"))
      result.errors must contain (FormError("value", "error.required"))
    }

    "must not bind an empty map" in {
      val result = testForm.bind(Map.empty[String, String])
      result.errors must contain(FormError("value", "error.required"))
    }

    "must return a custom error message" in {
      val form = Form("value" -> text("custom.error"))
      val result = form.bind(Map("value" -> ""))
      result.errors must contain(FormError("value", "custom.error"))
    }

    "must unbind a valid value" in {
      val result = testForm.fill("foobar")
      result.apply("value").value.value mustEqual "foobar"
    }
  }

  "boolean" - {

    val testForm: Form[Boolean] =
      Form(
        "value" -> boolean()
      )

    "must bind true" in {
      val result = testForm.bind(Map("value" -> "true"))
      result.get mustEqual true
    }

    "must bind false" in {
      val result = testForm.bind(Map("value" -> "false"))
      result.get mustEqual false
    }

    "must not bind a non-boolean" in {
      val result = testForm.bind(Map("value" -> "not a boolean"))
      result.errors must contain(FormError("value", "error.boolean"))
    }

    "must not bind an empty value" in {
      val result = testForm.bind(Map("value" -> ""))
      result.errors must contain(FormError("value", "error.required"))
    }

    "must not bind an empty map" in {
      val result = testForm.bind(Map.empty[String, String])
      result.errors must contain(FormError("value", "error.required"))
    }

    "must unbind" in {
      val result = testForm.fill(true)
      result.apply("value").value.value mustEqual "true"
    }
  }

  "int" - {

    val testForm: Form[Int] =
      Form(
        "value" -> int()
      )

    "must bind a valid integer" in {
      val result = testForm.bind(Map("value" -> "1"))
      result.get mustEqual 1
    }

    "must not bind an empty value" in {
      val result = testForm.bind(Map("value" -> ""))
      result.errors must contain(FormError("value", "error.required"))
    }

    "must not bind an empty map" in {
      val result = testForm.bind(Map.empty[String, String])
      result.errors must contain(FormError("value", "error.required"))
    }

    "must unbind a valid value" in {
      val result = testForm.fill(123)
      result.apply("value").value.value mustEqual "123"
    }
  }

  "enumerable" - {

    val testForm = Form(
      "value" -> enumerable[Foo]()
    )

    "must bind a valid option" in {
      val result = testForm.bind(Map("value" -> "Bar"))
      result.get mustEqual Bar
    }

    "must not bind an invalid option" in {
      val result = testForm.bind(Map("value" -> "Not Bar"))
      result.errors must contain(FormError("value", "error.invalid"))
    }

    "must not bind an empty map" in {
      val result = testForm.bind(Map.empty[String, String])
      result.errors must contain(FormError("value", "error.required"))
    }
  }
}
