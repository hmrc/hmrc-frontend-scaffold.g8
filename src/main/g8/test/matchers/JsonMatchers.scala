package matchers

import org.scalatest.matchers.{MatchResult, Matcher}
import play.api.libs.json._

trait JsonMatchers {

  class JsonContains(json: JsObject) extends Matcher[JsObject] {

    def apply(left: JsObject): MatchResult = {

      val mismatches = json.keys.filter(key => (left \ key) != (json \ key))

      MatchResult(
        mismatches.isEmpty,
        s"""\$left did not match for key(s) \${mismatches.mkString(", ")}""",
        s"""\$left matched for key(s) \${json.keys.mkString(", ")}"""
      )
    }
  }

  def containJson(expectedJson: JsObject) = new JsonContains(expectedJson)
}

object JsonMatchers extends JsonMatchers
