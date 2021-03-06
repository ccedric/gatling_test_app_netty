package io.gatling.netty.benchmark

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class FastWorkload extends Simulation {

  val config = ConfigFactory.load()

  val httpConf = http
    .baseURL(config.getString("frontline.benchmark.netty.webUrl")) // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("scenario1")
    .during(config.getInt("frontline.benchmark.netty.duration") seconds) {
        exec(
          http("hello")
            .get("/hello")
            .check(status.is(200)))
    }

  setUp(
    scn.inject(
      atOnceUsers(config.getInt("frontline.benchmark.netty.users"))))
    .protocols(httpConf)
    .assertions(
      global.failedRequests.count.is(0)
    )

}
