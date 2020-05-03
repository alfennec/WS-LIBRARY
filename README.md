# WS-LIBRARY

Le but du projet est le développement d’une solution de gestion d’inventaire, d’une bibliothèque, ou d’une médiathèque. 
A chaque élément (livre, vidéo …) est associé une cote, 
une série d’informations (année, titre, …) ainsi qu’un QRcode ). 
Un serveur mémorise l’ensemble de la collection avec les différentes informations, 
et offre des services d’accès (en REST) afin de pouvoir répondre aux requêtes du client.
Le client est une application mobile, qui permet de flasher les QRcode et d’échanger avec le serveur
pour connaitre les formations d’un élément, s’il a été emprunté ou non …

Pour la technologie mobile, je vous laisse libre d’utiliser celle que vous voulez, 
je demanderai juste dans le rapport un état de la programmation avec 
cette techno (et pourquoi pas quelques élément en vidéo pour montrer le fonctionnement) l’idéal serait que votre appli 
soit multiplateforme (android et IOS)

Pour la base de données, c’est à vous de voir et de faire l’état du besoin,
en fonction de la charge que vous estimez, peut être qu’une base à plat local suffirait,
si me dire quelle sont les contraintes pour chaque type de base

Pour les tables, c’est à vous de faire l’analyse des données

Les utilisateurs seront soit de type admin (ajout Dun utilisateur, d’un média …) soit de type utilisateur

Pour l’interface graphique il faut voir ce que vous allez définit comme élément de votre appication
