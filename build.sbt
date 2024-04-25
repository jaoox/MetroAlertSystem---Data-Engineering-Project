ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

libraryDependencies += "org.apache.flink" %% "flink-scala" % "1.13.2"

lazy val root = (project in file("."))
  .settings(
    name := "Data_Engenieur_project"
  )