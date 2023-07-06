package config

import play.api.Configuration
import uk.gov.hmrc.crypto.{Decrypter, Encrypter, SymmetricCryptoFactory}

import javax.inject.{Inject, Provider, Singleton}

@Singleton
class CryptoProvider @Inject() (
                                 configuration: Configuration
                               ) extends Provider[Encrypter with Decrypter] {

  override def get(): Encrypter with Decrypter =
    SymmetricCryptoFactory.aesGcmCryptoFromConfig("crypto", configuration.underlying)
}
