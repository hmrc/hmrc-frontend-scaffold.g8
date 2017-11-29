lazy val root = (project in file("."))
  .settings(

    name    := "root",
    version := "0.1.0",

    inConfig(Compile)(Giter8Plugin.baseGiter8Settings),

    baseDirectory := {
      file(sys.props.get("template-path").get)
    },

    sourceDirectory := {
      baseDirectory.value / "src"
    },

    target in (Compile, g8) := {
      file(("pwd" !!).stripLineEnd) / "g8"
    }
  )
