package simulation

import spray.json._
import spray.json.DefaultJsonProtocol._

object IoTJsonProtocol extends DefaultJsonProtocol {
  implicit object AnyJsonFormat extends JsonFormat[Any] {
    def write(value: Any): JsValue = value match {
      case n: Int => JsNumber(n)
      case n: Long => JsNumber(n)
      case n: Float => JsNumber(n)
      case n: Double => JsNumber(n)
      case s: String => JsString(s)
      case b: Boolean => JsBoolean(b)
      case l: List[_] => JsArray(l.map(write): _*)
      case m: Map[_, _] => JsObject(m.map { case (k, v) => k.toString -> write(v) })
      case _ => serializationError("Unsupported type")
    }

    def read(value: JsValue): Any = value match {
      case JsNumber(num) => num
      case JsString(str) => str
      case JsBoolean(bool) => bool
      case JsArray(elements) => elements.map(read)
      case JsObject(fields) => fields.map { case (k, v) => k -> read(v) }
      case _ => deserializationError("Unsupported type")
    }
  }

  implicit val mapFormat: JsonFormat[Map[String, Any]] = new JsonFormat[Map[String, Any]] {
    def write(m: Map[String, Any]): JsValue = JsObject(m.mapValues(AnyJsonFormat.write))
    def read(value: JsValue): Map[String, Any] = value.asJsObject.fields.mapValues(AnyJsonFormat.read)
  }

  implicit val listMapFormat: JsonFormat[List[Map[String, Any]]] = listFormat[Map[String, Any]]
}

object IoTProducer {
  import IoTJsonProtocol._

  def parseIoTData(jsonData: String): List[Map[String, Any]] = {
    jsonData.parseJson.convertTo[List[Map[String, Any]]]
  }
}
