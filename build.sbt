ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "org.apache.flink" %% "flink-scala" % "1.14.4",
  "org.apache.flink" %% "flink-streaming-scala" % "1.14.4",
  "org.apache.flink" %% "flink-connector-kafka" % "1.12.2",
  "org.apache.kafka" %% "kafka" % "2.7.0",
  "org.apache.kafka" % "kafka-clients" % "2.7.0",
  "org.json4s" %% "json4s-native" % "3.6.7",
  "org.json4s" %% "json4s-jackson" % "3.6.7",
  "io.spray" %% "spray-json" % "1.3.6",
  "org.apache.spark" %% "spark-core" % "3.1.2" exclude("org.scala-lang.modules", "scala-xml_2.12"),
  "org.apache.spark" %% "spark-sql" % "3.1.2" exclude("org.scala-lang.modules", "scala-xml_2.12")
)

libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.EarlySemVer

lazy val root = (project in file("."))
  .settings(
    name := "Data_Engineer_Project"
  )
