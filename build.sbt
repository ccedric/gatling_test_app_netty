enablePlugins(GatlingPlugin)

val allJavaOptions = Seq(
  "-server",
  "-XX:+UseG1GC",
  "-XX:+UseThreadPriorities", "-XX:ThreadPriorityPolicy=42",
  "-Xms2G", "-Xmx2G", "-Xmn100M", "-Xss10M",
  "-XX:+HeapDumpOnOutOfMemoryError",
  "-XX:+AggressiveOpts", "-XX:+OptimizeStringConcat", "-XX:+UseFastAccessorMethods",
//  "-XX:+UseParNewGC", "-XX:+UseConcMarkSweepGC",
//  "-XX:+CMSParallelRemarkEnabled",
  "-Djava.net.preferIPv4Stack=true", "-Djava.net.preferIPv6Addresses=false"
)

javaOptions in Gatling ++= overrideDefaultJavaOptions(allJavaOptions: _*)

scalaVersion := "2.11.8"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

val gatlingVersion = "2.3.0-SNAPSHOT"
val frontlineVersion = "2.2.0-SNAPSHOT"

val orgId = "f086a22e-8a41-4821-ab00-ae8f148c9964"
resolvers += "Gatling Corp's Third Party Repository" at s"http://repository.gatling.io/$orgId/content/repositories/thirdparty"
resolvers += "Gatling Corp's Repository" at s"http://repository.gatling.io/$orgId/content/repositories/snapshots"
resolvers += Resolver.mavenLocal

libraryDependencies += "io.gatling"           % "gatling-test-framework" % gatlingVersion   % "test" changing()
libraryDependencies += "io.gatling.frontline" % "frontline-probe"        % frontlineVersion % "test" changing()
libraryDependencies += "net.ceedubs"          %% "ficus"                 % "1.1.2"          % "test"

Project.inConfig(Test)(baseAssemblySettings)
assemblyMergeStrategy in (Test, assembly) := {
//  case "META-INF/BCKEY.RSA" => MergeStrategy.discard
//  case "META-INF/BCKEY.SF" => MergeStrategy.discard
//  case x if x.startsWith("org.bouncycastle") => MergeStrategy.discard
  case "META-INF/io.netty.versions.properties" => MergeStrategy.discard
  case x => (assemblyMergeStrategy in assembly).value(x)
}
jarName in (Test, assembly) := s"${name.value}-test-${version.value}.jar"
