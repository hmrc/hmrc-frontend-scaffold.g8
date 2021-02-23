import sbt._

object AppDependencies {
  import play.core.PlayVersion

  val compile = Seq(
    play.sbt.PlayImport.ws,
    "org.reactivemongo" %% "play2-reactivemongo"            % "0.20.11-play27",
    "org.reactivemongo" %% "reactivemongo-play-json-compat" % "0.20.11-play27",
    "com.typesafe.play" %% "play-iteratees"                 % "2.6.1",
    "uk.gov.hmrc"       %% "play-frontend-hmrc"             % "0.44.0-play-27",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping"  % "1.5.0-play-26",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-27"     % "4.0.0",
    "uk.gov.hmrc"       %% "play-language"                  % "4.10.0-play-27"
  )

  val test = Seq(
    "org.scalatest"               %% "scalatest"          % "3.2.0",
    "org.scalatestplus"           %% "scalacheck-1-14"    % "3.1.2.0",
    "org.scalatestplus"           %% "mockito-3-3"        % "3.1.2.0",
    "org.scalatestplus.play"      %% "scalatestplus-play" % "4.0.0",
    "org.pegdown"                 %  "pegdown"            % "1.6.0",
    "org.jsoup"                   %  "jsoup"              % "1.10.3",
    "com.typesafe.play"           %% "play-test"          % PlayVersion.current,
    "org.mockito"                 %% "mockito-scala"      % "1.16.0",
    "org.scalacheck"              %% "scalacheck"         % "1.14.3",
    "com.vladsch.flexmark"        %  "flexmark-all"       % "0.35.10" // Required to stay at this version - see https://github.com/scalatest/scalatest/issues/1736
  ).map(_ % "test, it")

  def apply(): Seq[ModuleID] = compile ++ test

  val akkaVersion = "2.6.7"
  val akkaHttpVersion = "10.1.12"

  val overrides = Seq(
    "com.typesafe.akka" %% "akka-stream_2.12" % akkaVersion,
    "com.typesafe.akka" %% "akka-protobuf_2.12" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j_2.12" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor_2.12" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core_2.12" % akkaHttpVersion
  )
}
