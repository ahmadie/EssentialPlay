name := "simple-vocabulary-teacher"

version := "1.0"

lazy val app = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion in app := "2.11.2"

libraryDependencies in app ++= Seq()

routesGenerator := InjectedRoutesGenerator

com.typesafe.sbt.SbtScalariform.scalariformSettings

routesImport += "binders.PathBinders._"