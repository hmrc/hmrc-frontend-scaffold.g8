package pages

import pages.behaviours.PageBehaviours


class $className$PageSpec extends PageBehaviours {

  "$className$Page" - {

    beRetrievable[String]($className$Page)

    beSettable[String]($className$Page)

    beRemovable[String]($className$Page)
  }
}
