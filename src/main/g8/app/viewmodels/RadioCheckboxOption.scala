package viewmodels

case class RadioCheckboxOption(id: String, value: String, messageKey: String)

object RadioCheckboxOption {
  def apply(keyPrefix: String, option: String): RadioCheckboxOption = RadioCheckboxOption(
    s"\$keyPrefix.\$option",
    option,
    s"\$keyPrefix.\$option"
  )
}
