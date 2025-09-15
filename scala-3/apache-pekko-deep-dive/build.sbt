ThisBuild / scalaVersion := "3.6.0"

lazy val root = (project in file("."))
  .settings(
    name := "pekko-demo",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor-typed" % "1.0.2"
    )
  )
