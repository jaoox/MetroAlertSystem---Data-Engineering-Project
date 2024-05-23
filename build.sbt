name := "Data_Engineer_Project"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.1.2",
  "org.apache.spark" %% "spark-sql" % "3.1.2",
  "org.apache.spark" %% "spark-streaming" % "3.1.2",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.1.2",
  "com.typesafe.akka" %% "akka-actor" % "2.6.4",
  "com.typesafe.akka" %% "akka-stream" % "2.6.4",
  "com.typesafe.akka" %% "akka-http" % "10.1.11",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11",
  "org.apache.kafka" %% "kafka" % "2.6.0",
  "org.apache.flink" %% "flink-scala" % "1.12.0",
  "org.apache.flink" %% "flink-streaming-scala" % "1.12.0",
  "io.spray" %% "spray-json" % "1.3.6",
  "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
  "org.apache.kafka" % "kafka-clients" % "2.7.0",
  "org.mongodb.spark" %% "mongo-spark-connector" % "2.4.1"
)

mainClass in Compile := Some("simulation.Main")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
