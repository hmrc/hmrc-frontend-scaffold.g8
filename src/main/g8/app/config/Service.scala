package config

import play.api.{ConfigLoader, Configuration}

import scala.language.implicitConversions

final case class Service(host: String, port: String, protocol: String) {

  def baseUrl: String =
    s"\$protocol://\$host:\$port"

  override def toString: String =
    baseUrl
}

object Service {

  implicit lazy val configLoader: ConfigLoader[Service] = ConfigLoader {
    config =>
      prefix =>

        val service  = Configuration(config).get[Configuration](prefix)
        val host     = service.get[String]("host")
        val port     = service.get[String]("port")
        val protocol = service.get[String]("protocol")

        Service(host, port, protocol)
  }

  implicit def convertToString(service: Service): String =
    service.baseUrl
}
