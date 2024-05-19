ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

libraryDependencies ++= Seq(
  "org.apache.flink" %% "flink-scala" % "1.14.4",
  "org.apache.flink" %% "flink-streaming-scala" % "1.14.4",
  "org.apache.flink" %% "flink-connector-kafka" % "1.12.2",
  "org.apache.kafka" %% "kafka" % "2.7.0",
  "org.apache.kafka" % "kafka-clients" % "2.7.0",
  "org.json4s" %% "json4s-native" % "3.6.7",
  "org.json4s" %% "json4s-jackson" % "3.6.7"
)

lazy val root = (project in file("."))
  .settings(
    name := "Data_Engenieur_project"
  )