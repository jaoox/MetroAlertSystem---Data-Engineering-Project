package simulation.model

case class Person(id: Int, position: Double, speed: Double) {
  def updatePosition(newPosition: Double): Person = {
    copy(position = newPosition)
  }
}