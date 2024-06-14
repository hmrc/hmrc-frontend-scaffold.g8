package forms.behaviours

import play.api.data.{Form, FormError}

trait IntFieldBehaviours extends FieldBehaviours {

  def intField(form: Form[_],
               fieldName: String,
               nonNumericError: FormError,
               wholeNumberError: FormError): Unit = {

    "not bind non-numeric numbers" in {

      forAll(nonNumerics -> "nonNumeric") {
        nonNumeric =>
          val result = form.bind(Map(fieldName -> nonNumeric)).apply(fieldName)
          result.errors must contain only nonNumericError
      }
    }

    "not bind decimals" in {

      forAll(decimals -> "decimal") {
        decimal =>
          val result = form.bind(Map(fieldName -> decimal)).apply(fieldName)
          result.errors must contain only wholeNumberError
      }
    }

    "not bind integers larger than Int.MaxValue" in {

      forAll(intsLargerThanMaxValue -> "massiveInt") {
        (num: BigInt) =>
          val result = form.bind(Map(fieldName -> num.toString)).apply(fieldName)
          result.errors must contain only nonNumericError
      }
    }

    "not bind integers smaller than Int.MinValue" in {

      forAll(intsSmallerThanMinValue -> "massivelySmallInt") {
        (num: BigInt) =>
          val result = form.bind(Map(fieldName -> num.toString)).apply(fieldName)
          result.errors must contain only nonNumericError
      }
    }
  }

  def intFieldWithMinimum(form: Form[_],
                          fieldName: String,
                          minimum: Int,
                          expectedError: FormError): Unit = {

    s"not bind integers below \$minimum" in {

      forAll(intsBelowValue(minimum) -> "intBelowMin") {
        (number: Int) =>
          val result = form.bind(Map(fieldName -> number.toString)).apply(fieldName)
          result.errors must contain only expectedError
      }
    }
  }
  
  def intFieldWithMaximum(form: Form[_],
                          fieldName: String,
                          maximum: Int,
                          expectedError: FormError): Unit = {
    
    s"not bind integers above \$maximum" in {
      
      forAll(intsAboveValue(maximum) -> "intAboveMax") {
        (number: Int) =>
          val result = form.bind(Map(fieldName -> number.toString)).apply(fieldName)
          result.errors must contain only expectedError
      }
    }
  }
  
  def intFieldWithRange(form: Form[_],
                        fieldName: String,
                        minimum: Int,
                        maximum: Int,
                        expectedError: FormError): Unit = {
    
    s"not bind integers outside the range \$minimum to \$maximum" in {
      
      forAll(intsOutsideRange(minimum, maximum) -> "intOutsideRange") {
        (number: Int) =>
          val result = form.bind(Map(fieldName -> number.toString)).apply(fieldName)
          result.errors must contain only expectedError
      }
    }
  }
}
