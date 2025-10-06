lazy val root = (project in file("."))
  .enablePlugins(ScriptedPlugin)
  .settings(

    name := "hmrc-frontend-scaffold.g8",

    scriptedLaunchOpts ++= { scriptedLaunchOpts.value ++
      Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value, s"-Dtemplate-path=${file(".").getAbsolutePath.dropRight(2)}")
    },

    // disable this to help with debugging tests
    scriptedBufferLog := true,

    Test / test := {
      (Test / scripted).toTask("").value
    }
  )
