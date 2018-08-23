package pages

import pages.behaviours.PageBehaviours

class $className$PageSpec extends PageBehaviours {

  "$className$Page" must {

    beRetrievable[Boolean]($className$Page)

    beSettable[Boolean]($className$Page)

    beRemovable[Boolean]($className$Page)
  }
}
