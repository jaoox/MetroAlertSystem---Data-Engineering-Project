# Application de Détection de Personnes sur les Rails de Métro

Ce projet vise à développer une application capable de détecter la présence de personnes sur les rails de métro et de déclencher une alerte secours en cas de danger. Voici les détails de chaque composant de notre architecture :

## Capteurs de Détection sur les Rails
Installer des capteurs le long des rails de métro pour détecter la présence de personnes et collecter des données en temps réel sur l'occupation des voies.

## Système de Traitement des Données de Capteurs
Développer un système pour traiter les données provenant des capteurs de manière efficace et en temps réel. Ce système identifiera les situations d'urgence et déclenchera des alertes lorsque la présence de personnes sur les rails est détectée.

## Gestion des Alertes Secours
Mettre en place un mécanisme pour gérer les alertes secours déclenchées par le système de détection. Les alertes doivent être transmises aux autorités compétentes et aux équipes de secours pour intervention immédiate.

## Interface Utilisateur pour la Surveillance
Développer une interface utilisateur intuitive qui permettra aux opérateurs du métro de surveiller en temps réel l'état des voies et de recevoir les alertes en cas de présence de personnes sur les rails.
   
### Technologies et Méthodes
Capteurs IoT : Utilisation de capteurs IoT pour la détection de personnes sur les rails de métro, avec une transmission des données en temps réel.
Scala et Programmation Fonctionnelle : Tout le code sera écrit en Scala en suivant les principes de la programmation fonctionnelle, pour garantir la fiabilité et la maintenabilité du système.
Traitement en Temps Réel : Utilisation de technologies de traitement des données en temps réel telles que Apache Kafka pour assurer une réactivité maximale du système.
Gestion des Alertes : Développement d'un système robuste de gestion des alertes pour garantir que les interventions de secours sont effectuées rapidement et efficacement.

# Plan d'Action
## Conception des Capteurs :
Déterminer les spécifications techniques des capteurs de poids.
## Développement de l'Outil de Simulation :
Créer un outil de simulation en Scala qui émule le comportement des capteurs.
L'outil générera des données basées sur des scénarios prédéfinis pour le poids détecté.
## Architecture de Stockage :
Mettre en place une instance InfluxDB pour recevoir les données en temps réel.
Configurer des requêtes et alertes pour détecter et répondre immédiatement aux incidents.
## Intégration et Tests :
Intégrer l'outil de simulation avec InfluxDB pour tester le flux de données.
Effectuer des tests approfondis pour s'assurer de la précision et de la réactivité des alertes.
## Déploiement et Surveillance :
Installer les capteurs sur les sites pilotes.
Surveiller les performances et ajuster le système au besoin.
## Documentation et Formation :
Rédiger une documentation détaillée pour le système.
Former les équipes de sécurité du métro à l'utilisation du système.
## Gestion de Projet :
Utiliser Git pour la gestion du code et le suivi des changements.
Assurer la conformité avec les normes de sécurité et de protection de la vie privée.