package simulation.movement

import simulation.model._

object PersonMovement {
  // Mettre à jour la position de chaque personne en fonction de la vitesse
  def updatePersonPosition(people: List[Person]): List[Person] = {
    val updatedPeople = people.map { person =>
      val newPosition = person.position + person.speed
      new Person(person.id, newPosition, person.speed) // Créer une nouvelle personne avec la position mise à jour
    }
    updatedPeople
  }
}
