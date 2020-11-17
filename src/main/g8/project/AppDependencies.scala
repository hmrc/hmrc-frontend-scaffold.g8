import sbt._

object AppDependencies {
  import play.core.PlayVersion

  val compile = Seq(
    play.sbt.PlayImport.ws,
    "org.reactivemongo" %% "play2-reactivemongo"            % "0.18.6-play27",
    "uk.gov.hmrc"       %% "logback-json-logger"            % "4.6.0",
    "uk.gov.hmrc"       %% "govuk-template"                 % "5.57.0-play-27",
    "uk.gov.hmrc"       %% "play-health"                    % "3.15.0-play-27",
    "uk.gov.hmrc"       %% "play-ui"                        % "8.12.0-play-27",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping"  % "1.3.0-play-26",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-27"     % "2.25.0",
    "uk.gov.hmrc"       %% "play-language"                  % "4.4.0-play-27"
  )

  val test = Seq(
    "org.scalatest"               %% "scalatest"          % "3.0.7",
    "org.scalatestplus.play"      %% "scalatestplus-play" % "3.1.2",
    "org.pegdown"                 %  "pegdown"            % "1.6.0",
    "org.jsoup"                   %  "jsoup"              % "1.10.3",
    "com.typesafe.play"           %% "play-test"          % PlayVersion.current,
    "org.mockito"                 %  "mockito-all"        % "1.10.19",
    "org.scalacheck"              %% "scalacheck"         % "1.14.0"
  ).map(_ % Test)

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
