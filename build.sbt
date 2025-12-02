lazy val root = (project in file("."))
  .settings(
    name := "hmrc-frontend-scaffold.g8",
    resolvers += Resolver.url("typesafe", url("https://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns)
  )