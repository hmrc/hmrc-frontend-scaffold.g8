package viewmodels

import base.SpecBase

class RadioCheckboxOptionSpec extends SpecBase {

  "Radio Checkbox Option" must {

    "build correctly from a key prefix and option" in {

      val radioCheckboxOption = RadioCheckboxOption("prefix", "option")

      radioCheckboxOption.id mustEqual "prefix.option"
      radioCheckboxOption.value mustEqual "option"
      radioCheckboxOption.messageKey mustEqual "prefix.option"
    }
  }
}
