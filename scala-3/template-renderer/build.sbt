ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.3"

lazy val root = (project in file("."))
  .settings(
    name := "template-renderer",
    version := "1.0",
    libraryDependencies ++= Seq(
      "com.itextpdf" % "itext-core" % "9.1.0",
      "org.slf4j" % "slf4j-simple" % "2.0.17",
      "org.scalatest" %% "scalatest" % "3.2.16" % Test
    )
  )
