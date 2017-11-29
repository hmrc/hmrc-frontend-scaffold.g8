lazy val root = (project in file("."))
  .settings(

    name := "hmrc-frontend-scaffold.g8",

    ScriptedPlugin.scriptedSettings,
    scriptedLaunchOpts := { scriptedLaunchOpts.value ++
      Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value, s"-Dtemplate-path=${file(".").getAbsolutePath.dropRight(2)}")
    },

    // disable this to help with debugging tests
    scriptedBufferLog := true,

    test in Test := {
      (scripted in Test).toTask("").value
    }
  )
