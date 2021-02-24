package viewmodels.govuk

import viewmodels.WithName

sealed trait Phase

object Phase {
  case object Alpha extends WithName("alpha") with Phase
  case object Beta extends WithName("beta") with Phase
}
