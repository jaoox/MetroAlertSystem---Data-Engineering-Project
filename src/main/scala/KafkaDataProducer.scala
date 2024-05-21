package simulation

import java.util.Properties
import scala.io.Source
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object KafkaDataProducer {
  def main(args: Array[String]): Unit = {
    // Properties for Kafka producer
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "all")
    props.put("batch.size", "16384") // Increase batch size
    props.put("linger.ms", "5") // Slightly delay to allow batching
    props.put("buffer.memory", "33554432") // Increase buffer memory

    // Create Kafka producer
    val producer = new KafkaProducer[String, String](props)
    
    // File path to the JSON data
    val filePath = "src/main/scala/resources/simulation_data.json"
    
    // Read the JSON file
    println(s"Reading data from $filePath")
    val source = Source.fromFile(filePath)
    val data = source.getLines().toList
    source.close()
    println(s"Read ${data.length} lines from $filePath")
    
    // Send each line as a Kafka message asynchronously
    data.foreach { jsonLine =>
      println(s"Sending line to Kafka: $jsonLine")
      producer.send(new ProducerRecord[String, String]("metro-data", jsonLine))
    }

    // Close the producer
    producer.close()
    println("Kafka producer closed")
  }
}
