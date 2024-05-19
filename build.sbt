ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

libraryDependencies ++= Seq(
  "org.apache.flink" %% "flink-scala" % "1.19.0",
  "org.apache.kafka" % "kafka-clients" % "3.1.0",
  "org.apache.flink" %% "flink-connector-kafka" % "1.19.0",
  "org.apache.flink" % "flink-hadoop-compatibility" % "1.19.0",
  "org.apache.flink" % "flink-hdfs" % "1.19.0"
)

lazy val root = (project in file("."))
  .settings(
    name := "Data_Engineer_project"
  )
