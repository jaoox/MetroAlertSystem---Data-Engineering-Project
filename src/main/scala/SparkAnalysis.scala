package simulation.analysis

import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._

object SparkAnalysis {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark Analysis")
      .getOrCreate()

    val dataPath = "simulation_data.json"
    val dataDF = loadData(spark, dataPath)

    val resultDF = performAnalysis(dataDF)
    resultDF.show()
    
    saveResults(resultDF, "analysis_results.json")

    spark.stop()
  }

  def loadData(spark: SparkSession, path: String): DataFrame = {
    spark.read.json(path)
  }

  def performAnalysis(dataDF: DataFrame): DataFrame = {
    dataDF.groupBy("scenario", "hour")
      .agg(avg("speed").as("average_speed"), count("personId").as("count"))
      .orderBy("scenario", "hour")
  }

  def saveResults(df: DataFrame, path: String): Unit = {
    df.write.mode("overwrite").json(path)
  }
}
