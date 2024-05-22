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
      
      case m: Map[_, _] => JsObject(m.view.mapValues(write).toMap)
      case Some(v) => write(v)
      case None => JsNull
      case _ => serializationError("Unsupported type")
    }

    def read(value: JsValue): Any = value match {
      case JsNumber(num) => if (num.isValidInt) num.toInt else if (num.isValidLong) num.toLong else num.toDouble
      case JsString(str) => str
      case JsBoolean(bool) => bool
      case JsArray(elements) => elements.map(read)
      case JsObject(fields) => fields.view.mapValues(read).toMap
      case JsNull => None
      case _ => deserializationError("Unsupported type")
    }
  }

  implicit val mapFormat: JsonFormat[Map[String, Any]] = new JsonFormat[Map[String, Any]] {
    def write(m: Map[String, Any]): JsValue = JsObject(m.view.mapValues(AnyJsonFormat.write).toMap)
    def read(value: JsValue): Map[String, Any] = value.asJsObject.fields.view.mapValues(AnyJsonFormat.read).toMap
  }

  implicit val listMapFormat: JsonFormat[List[Map[String, Any]]] = listFormat[Map[String, Any]]
}

import IoTJsonProtocol._

object IoTProducer {
  def parseData(jsonData: String): List[Map[String, Any]] = {
    jsonData.parseJson.convertTo[List[Map[String, Any]]]
  }
}
