ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.3"

lazy val root = (project in file("."))
  .settings(
    name := "json4s-scala3",
    version := "1.0",
    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-jackson" % "4.0.6",
      "org.json4s" %% "json4s-ext" % "4.0.6"
    )
  )
