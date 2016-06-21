enablePlugins(GatlingPlugin)

scalaVersion := "2.11.8"

val frontlineVersion = "2.2.3-SNAPSHOT"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % "test"
libraryDependencies += "io.gatling" % "gatling-test-framework" % "2.2.2" % "test"
libraryDependencies += "io.gatling.frontline" % "frontline-probe"        % frontlineVersion % "test" changing()

Project.inConfig(Test)(baseAssemblySettings)
assemblyMergeStrategy in (Test, assembly) := {
  case "META-INF/io.netty.versions.properties" => MergeStrategy.discard
  case x => (assemblyMergeStrategy in assembly).value(x)
}
jarName in (Test, assembly) := s"${name.value}-test-${version.value}.jar"
