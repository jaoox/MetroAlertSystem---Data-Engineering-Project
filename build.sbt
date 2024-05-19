ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

libraryDependencies += "org.apache.flink" %% "flink-scala" % "1.19.0"

lazy val root = (project in file("."))
  .settings(
    name := "Data_Engenieur_project"
  )