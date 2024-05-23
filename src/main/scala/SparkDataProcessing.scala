package simulation

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import java.util.Properties
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import scala.collection.JavaConverters._
import simulation.model.MetroData
import org.apache.log4j.{Level, Logger}

object SparkDataProcessing {

  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(getClass.getName)
    logger.setLevel(Level.INFO)

    val spark = SparkSession.builder
      .appName("Spark Data Processing")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "spark-processing-group")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(java.util.Arrays.asList("metro-data"))

    while (true) {
      val records = consumer.poll(1000).asScala
      val data = records.map(record => parseData(record.value())).toSeq
      val df = spark.createDataFrame(data)
      df.createOrReplaceTempView("metro_data")

      val alertDF = spark.sql("SELECT * FROM metro_data WHERE scenario = 'off' AND position < 300")
      alertDF.show()
    }
  }

  def parseData(record: String): MetroData = {
    implicit val formats = org.json4s.DefaultFormats
    org.json4s.jackson.JsonMethods.parse(record).extract[MetroData]
  }
}
