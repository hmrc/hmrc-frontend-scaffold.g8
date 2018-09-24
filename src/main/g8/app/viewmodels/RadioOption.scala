package viewmodels

case class RadioOption(id: String, value: String, messageKey: String)

object RadioOption {
  def apply(keyPrefix: String, option: String): RadioOption = RadioOption(
    s"\$keyPrefix.\$option",
    option,
    s"\$keyPrefix.\$option"
  )
}
