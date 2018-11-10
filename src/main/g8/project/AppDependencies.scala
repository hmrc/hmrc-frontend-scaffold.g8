import sbt._

object AppDependencies {
  import play.core.PlayVersion

  val compile = Seq(
    play.sbt.PlayImport.ws,
    "org.reactivemongo" %% "play2-reactivemongo"            % "0.16.0-play26",
    "uk.gov.hmrc"       %% "logback-json-logger"            % "3.1.0",
    "uk.gov.hmrc"       %% "govuk-template"                 % "5.25.0-play-26",
    "uk.gov.hmrc"       %% "play-health"                    % "3.7.0-play-26",
    "uk.gov.hmrc"       %% "play-ui"                        % "7.25.0-play-26",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping"  % "0.2.0",
    "uk.gov.hmrc"       %% "bootstrap-play-26"              % "0.27.0",
    "uk.gov.hmrc"       %% "play-whitelist-filter"          % "2.0.0"
  )

  val test = Seq(
    "uk.gov.hmrc"                 %% "hmrctest"           % "3.1.0",
    "org.scalatest"               %% "scalatest"          % "3.0.4",
    "org.scalatestplus.play"      %% "scalatestplus-play" % "2.0.1",
    "org.pegdown"                 %  "pegdown"            % "1.6.0",
    "org.jsoup"                   %  "jsoup"              % "1.10.3",
    "com.typesafe.play"           %% "play-test"          % PlayVersion.current,
    "org.mockito"                 %  "mockito-all"        % "1.10.19",
    "org.scalacheck"              %% "scalacheck"         % "1.13.4"
  ).map(_ % Test)

  def apply(): Seq[ModuleID] = compile ++ test
}
