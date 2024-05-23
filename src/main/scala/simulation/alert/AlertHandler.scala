package simulation.alert

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import spray.json._
import simulation.model.{MetroData, MetroDataProtocol}
import org.apache.log4j.{Level, Logger}

object AlertHandler {
  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(getClass.getName)
    logger.setLevel(Level.INFO)

    // Créer une session Spark
    val spark = SparkSession.builder()
      .appName("AlertHandler")
      .config("spark.master", "local")
      .getOrCreate()

    import spark.implicits._

    // Configuration de Kafka
    val kafkaBootstrapServers = "localhost:9092"
    val kafkaTopic = "metro-data"

    // Lire les données de Kafka
    val kafkaDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkaBootstrapServers)
      .option("subscribe", kafkaTopic)
      .option("startingOffsets", "earliest")
      .load()

    // Convertir les données de Kafka en chaîne de caractères
    val kafkaData = kafkaDF.selectExpr("CAST(value AS STRING)").as[String]

    // Convertir les données JSON en DataFrame de MetroData
    val metroDataDF = kafkaData.map(record => record.parseJson.convertTo[MetroData]).toDF()

    // Filtrer les données pour générer des alertes
    val alertDF = metroDataDF.filter($"scenario" === "off" && $"position" < 300)

    // Écrire les alertes dans la console (ou dans un autre sink comme HDFS, S3, etc.)
    val query = alertDF.writeStream
      .outputMode("append")
      .format("console")
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .start()

    // Attendre la fin de l'exécution
    query.awaitTermination()
  }
}
