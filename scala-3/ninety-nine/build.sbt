ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.3"

lazy val root = (project in file("."))
  .settings(
    name := "ninety-nine",
    version := "1.0",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.16" % Test
    )
  )
