Voici notre projet de POC redéfini, orienté vers une application mobile utilisée dans le métro pour suivre la localisation GPS des utilisateurs. Notre but est de déterminer les moments et les lieux de saturation dans les rames de métro et les stations, et de poser les bases pour des modèles d'optimisation du trafic futurs. Voici les détails de chaque composant de notre architecture :

### 1. **Simulation des utilisateurs de l'application**
- **Objectif :** Nous devons créer un simulateur qui produit des données utilisateurs en envoyant des coordonnées GPS à notre système chaque minute. Ce simulateur nous permettra de tester notre système en générant des données en temps réel.

### 2. **Gestion des alertes de saturation**
- **Objectif :** Il faut développer un mécanisme pour traiter les données GPS en temps réel et identifier les situations de surcharge dans les rames ou les stations pour comprendre les dynamiques de mouvement et les points de surcharge.

### 3. **Stockage des données GPS**
- **Objectif :** Nous allons stocker les informations de localisation dans un système de stockage distribué comme HDFS ou Amazon S3. Les données doivent être organisées pour faciliter les analyses futures et pour supporter l'intégration avec des modèles d'optimisation de trafic.

### 4. **Analyse des données pour l'optimisation du trafic**
- **Objectif :** L'utilisation d'Apache Spark nous permettra de traiter et d'analyser les données accumulées pour identifier les points de saturation et répondre à des questions spécifiques sur le trafic. Plus tard, nous envisagerons de développer des modèles prédictifs pour améliorer la gestion du trafic métro.

### **Technologies et méthodes**
- **Streaming avec Kafka :** Apache Kafka gérera le flux des données en temps réel, garantissant ainsi une haute disponibilité et réactivité.
- **Scala et Programmation Fonctionnelle :** Tout notre code doit être écrit en Scala en suivant les principes de la programmation fonctionnelle, en respectant les restrictions sur l'utilisation des structures impératives et en favorisant l'immuabilité.
- **Spark pour l'Analyse :** Nous utiliserons Spark pour les tâches d'analyse lourdes, permettant une manipulation efficace des grands volumes de données.
- **Stockage :** Le choix de HDFS ou Amazon S3 pour le stockage des données nous offre la flexibilité et la scalabilité nécessaires pour le projet.


Notre approche doit être méthodique et conforme aux standards de sécurité et de protection de la vie privée, étant donné la nature sensible des données GPS. L'architecture doit être solide et scalable et doit pouvoir évoluer selon les besoins du projet.
