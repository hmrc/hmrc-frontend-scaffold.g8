import sbt._

object AppDependencies {

  private val bootstrapVersion = "7.13.0"
  private val hmrcMongoVersion = "0.74.0"

  val compile = Seq(
    play.sbt.PlayImport.ws,
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-28"     % bootstrapVersion,
    "uk.gov.hmrc"       %% "play-frontend-hmrc"             % "6.3.0-play-28",
    "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-28"             % "0.74.0"
  )

  val test = Seq(
    "uk.gov.hmrc"          %% "bootstrap-test-play-28"  % bootstrapVersion,
    "uk.gov.hmrc.mongo"    %% "hmrc-mongo-test-play-28" % hmrcMongoVersion,
    "org.jsoup"            %  "jsoup"                   % "1.14.3",
    "org.scalatestplus"    %% "scalacheck-1-15"         % "3.2.3.0",
    "org.scalatestplus"    %% "mockito-3-4"             % "3.2.3.0",
  ).map(_ % "test, it")

  def apply(): Seq[ModuleID] = compile ++ test
}
