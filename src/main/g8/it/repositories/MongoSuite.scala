package repositories

import com.typesafe.config.ConfigFactory
import org.scalatest._
import play.api.Configuration
import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object MongoSuite {

  private lazy val config = Configuration(ConfigFactory.load(System.getProperty("config.resource")))

  lazy val connection: Future[(ParsedURI, MongoConnection)] =
    for {
      parsedUri  <- MongoConnection.fromString(config.get[String]("mongodb.uri"))
      connection <- AsyncDriver().connect(parsedUri)
    } yield (parsedUri, connection)
}

trait MongoSuite {
  self: TestSuite =>

  def database: Future[DefaultDB] =
    MongoSuite.connection.flatMap {
      case (uri, connection) =>
        connection.database(uri.db.get)
    }
}
