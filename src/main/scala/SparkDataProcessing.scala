package simulation

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object SparkDataProcessing {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("Spark Data Processing")
      .master("local[*]")
      .config("spark.mongodb.output.uri", "mongodb://localhost:27017/sparkData.metroData")
      .getOrCreate()

    import spark.implicits._

    // Define the schema according to your JSON data
    val schema = new StructType()
      .add("hour", IntegerType)
      .add("personId", IntegerType)
      .add("position", DoubleType)
      .add("scenario", StringType)
      .add("speed", DoubleType)
      .add("station", StringType)
      .add("timestamp", LongType)

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "metro-data")
      .option("startingOffsets", "earliest")
      .load()
      .selectExpr("CAST(value AS STRING) as value")
      .select(from_json($"value", schema).as("data"))
      .select("data.*")

    val alertDF = df.filter($"scenario" === "alert" && $"position" < 300)

    val query = alertDF.writeStream
      .outputMode("append")
      .format("console")
      .start()

    df.writeStream
      .foreachBatch { (batchDF: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row], _: Long) =>
        batchDF.write
          .format("mongo")
          .mode("append")
          .save()
      }
      .start()
      .awaitTermination()
  }
}
