# Backend plateforme de contenu vidéo

## Objectif : 
L’exercice a pour but de mettre en place un backend complet utilisant Java & Spring Boot. Ce backend fournira une API REST à interroger via des requêtes HTTP. Le but de cet exercice est de mettre en pratique les technologies Java & Spring boot permettant de modéliser rapidement une API REST avec un code dont l’architecture est basée sur le modèle MVC
Pour répondre aux exigences de l’exercice, un sujet est proposé, il s’agit d’une plateforme vidéo (dans le genre YouTube, Dailymotion, etc.) très largement simplifiée.

## Technologies : 
- Java 17+
- Maven Dépendances accessibles via spring initializr :
  - Spring boot web
  - H2
  - JPA
  - Spring boot devtools
  - Validation
- Swagger 

## Fonctionnement : 
1. Démarrer le projet avec la commande : ` mvn spring-boot:run `
2. [URL du swagger ](http://localhost:8080/swagger-ui/index.html#/)
3. Créer un User avec un rôle prédéfini  :
    - récupérer le rôle depuis ` role-controller GET /roles `
    - créer l'user depuis ` user-controller POST /users `
4. Créer un Tag : ` tag-controller POST /tags `

   ### Fonctionnalité : 

- Recherche d’utilisateur par username « similaires » (contains) : ` user-controller GET /search `
- Recherche de tous les utilisateurs appartenant à un rôle : ` role-controller GET /roles/{roleTitle}/users `
- Recherche de tags par titre similaire : ` tag-controller GET /tag/similaire/{tagTitle} ` 
- Recherche de vidéos par titre de tag exact : ` video-controller GET /videos/tag/{tagTitle} `
- Recherche de vidéos par titre ou description courte « similaire » : ` video-controller GET /videos/similaire/titre/{videoTitle} ` ou ` video-controller GET /videos/similaire/description/{videoDesc} `
