package frontline

import scala.concurrent.duration.FiniteDuration

import com.typesafe.config.ConfigFactory
import scala.util.Properties._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

trait Configured {

  val environment = propOrElse("environment", "test")

  val settings = {
    val configFromProperties = ConfigFactory.systemProperties()
    val configFromFile = ConfigFactory.parseResources(getClass.getClassLoader, s"frontline-$environment.conf")
    val config = configFromProperties.withFallback(configFromFile)

    config.as[Settings]("frontline")
  }
}

case class Settings(benchmarks: Benchmarks) {

  def baseHttpProtocol(webUrl: String): HttpProtocolBuilder = {
    val protocol =
      http.baseURL(webUrl)
        .acceptEncodingHeader("gzip, deflate")
        .disableCaching
        .disableUrlEncoding
        .disableWarmUp
        .shareConnections

    protocol
  }
}

case class Benchmarks(fastWorkload: FastWorkload)
case class FastWorkload(webUrl: String, users: Int, duration: FiniteDuration)
