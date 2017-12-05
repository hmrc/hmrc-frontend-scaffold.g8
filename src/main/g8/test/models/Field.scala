package models

case class Field(name: String, errorKeys: Map[ErrorType, String])

object Field {

  def apply(name: String, errors: (ErrorType, String)*): Field =
    Field(name, errors.toMap)
}

sealed trait ErrorType
case object Required extends ErrorType
case object Invalid extends ErrorType
