package pages

import models.$className$
import pages.behaviours.PageBehaviours

class $className$Spec extends PageBehaviours {

  "YourLocation" must {

    beRetrievable[$className$]($className$Page)

    beSettable[$className$]($className$Page)

    beRemovable[$className$]($className$Page)
  }
}
