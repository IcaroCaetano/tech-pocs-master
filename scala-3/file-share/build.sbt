ThisBuild / scalaVersion := "3.6.0"

lazy val root = (project in file("."))
  .settings(
    name := "file-share",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.16" % Test
    )
  )
