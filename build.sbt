name := "Data_Engineer_Project"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
<<<<<<< HEAD
  "org.apache.flink" %% "flink-scala" % "1.19.0",
  "org.apache.flink" %% "flink-streaming-scala" % "1.19.0",
  "org.apache.flink" %% "flink-connector-kafka" % "1.15.3",
  "org.apache.flink" %% "flink-clients" % "1.19.0",
  "org.apache.kafka" %% "kafka" % "3.0.0",
  "org.apache.kafka" % "kafka-clients" % "3.0.0",
  "org.json4s" %% "json4s-native" % "3.6.11",
  "org.json4s" %% "json4s-jackson" % "3.6.11",
  "io.spray" %% "spray-json" % "1.3.6",
  "org.apache.spark" %% "spark-core" % "3.3.0" exclude("org.scala-lang.modules", "scala-xml_2.12"),
  "org.apache.spark" %% "spark-sql" % "3.3.0" exclude("org.scala-lang.modules", "scala-xml_2.12")
)

lazy val root = (project in file("."))
  .settings(
    name := "Data_Engineer_Project"
  )
=======
  "org.apache.spark" %% "spark-core" % "3.1.2",
  "org.apache.spark" %% "spark-sql" % "3.1.2",
  "com.typesafe.akka" %% "akka-actor" % "2.6.4",
  "com.typesafe.akka" %% "akka-stream" % "2.6.4",
  "com.typesafe.akka" %% "akka-http" % "10.1.11",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11",
  "org.apache.kafka" %% "kafka" % "2.6.0",
  "org.apache.flink" %% "flink-scala" % "1.12.0",
  "org.apache.flink" %% "flink-streaming-scala" % "1.12.0",
  "io.spray" %% "spray-json" % "1.3.6",
  "org.scala-lang.modules" %% "scala-xml" % "1.3.0"
)

mainClass in Compile := Some("simulation.Main")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
>>>>>>> origin/joao
