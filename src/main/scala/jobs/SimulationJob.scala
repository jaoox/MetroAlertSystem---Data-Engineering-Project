import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object SimulationJob extends App {
  val env = StreamExecutionEnvironment.getExecutionEnvironment

  // Configuration et lancement des sources de données simulées et des transformations
  // ...

  env.execute("Traffic Data Simulation Job")
}
