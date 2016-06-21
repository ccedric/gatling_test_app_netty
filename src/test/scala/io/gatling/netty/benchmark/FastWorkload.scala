package io.gatling.netty.benchmark

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._


class FastWorkload extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8000") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("scenario1")
    .during(1 minute) {
      exec(
        http("hello")
          .get("/hello")
          .check(status.is(200)))
        .exec(
          http("json1k")
            .get("/json1k")
            .check(status.is(200)))
        .exec(
          http("json10k")
            .get("/json10k")
            .check(status.is(200)))
        .exec(
          http("json100k")
            .get("/json100k")
            .check(status.is(200)))
    }

  setUp(
    scn.inject(
      atOnceUsers(2000)))
    .protocols(httpConf)
}
