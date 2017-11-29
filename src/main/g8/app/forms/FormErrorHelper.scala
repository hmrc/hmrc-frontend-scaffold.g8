package forms

import play.api.data.FormError

trait FormErrorHelper {
  def produceError(key: String, error: String) = Left(Seq(FormError(key, error)))
}
