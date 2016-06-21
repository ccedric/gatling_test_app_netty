package frontline.benchmarks

import frontline.Configured
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FastWorkload extends Simulation
  with Configured {

  import settings._

  val fastWorkload = benchmarks.fastWorkload
  val httpProtocol =
    baseHttpProtocol(fastWorkload.webUrl)

  val scn = scenario("scenario1")
    .during(fastWorkload.duration) {
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
      atOnceUsers(fastWorkload.users)))
    .protocols(httpProtocol)
}
