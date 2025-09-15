val circeVersion = "0.14.1"

lazy val root = (project in file("."))
  .settings(
    name := "scala-project",
    scalaVersion := "3.6.3",
    libraryDependencies ++= Seq(
      "com.github.pureconfig" %% "pureconfig-core" % "0.17.8",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.scalatest" %% "scalatest" % "3.2.19" % "test"
    )
  )
