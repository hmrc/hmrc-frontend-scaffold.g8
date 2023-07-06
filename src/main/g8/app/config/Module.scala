package config

import controllers.actions._
import play.api.inject.Binding
import play.api.{Configuration, Environment}
import uk.gov.hmrc.crypto.{Decrypter, Encrypter}

import java.time.Clock

class Module extends play.api.inject.Module {

  override def bindings(environment: Environment, configuration: Configuration): collection.Seq[Binding[_]] = {

    Seq(
      bind[DataRetrievalAction].to[DataRetrievalActionImpl].eagerly(),
      bind[DataRequiredAction].to[DataRequiredActionImpl].eagerly(),
      bind[IdentifierAction].to[AuthenticatedIdentifierAction].eagerly(), // Change to SessionIdentifierAction for session-based storage
      bind[Clock].toInstance(Clock.systemUTC()),
      bind[Encrypter with Decrypter].toProvider[CryptoProvider]
    )
  }
}
