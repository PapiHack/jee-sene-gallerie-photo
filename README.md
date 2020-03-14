# Sène Gallerie Photo

Petite application de partage d'albums photo dans le cadre de mon cours de JEE. Il est possible de visiter la gallerie afin de visualiser les albums photo définis comme étant public. Quant aux albums privés, seul les utilisateurs autorisés peuvent les voir. 
Les utilisateurs sont de deux types : 
- Les utilisateurs simples (ou visiteurs) qui peuvent s'inscrirent, se connecter, créer des albums , etc.
- Les administrateurs gérant l'application et les utilisateurs. 

## Stack Technique  
- Java JEE
- GlassFish 5.1.0
- JSTL
- JPA
- MySQL
- Bootstrap, Font Awesome
- jQuery

## Architecture du projet  

Pour une meilleur organisation, j'ai découpé l'appplication en packages ou couches effectuant des tâches bien scpécifiques :  

- `sn.sgp.beans`  
Contient les beans ou entités également appelé classe métier de l'application.  

- `sn.sgp.managers`  
Correspond à la couche `DAO` de l'application et contient les classes permettant de persister les entités (beans) correspondant en base de donnée.  

- `sn.sgp.filters`  
Contient nos filtres qui sont des sortes de `middleware` et permettant d'effectuer ou de déclencher certaines opérations entre la requête et la réponse.  

- `sn.sgp.servlets`  
Contient les servlets de l'application et font office de controller. Ce package correspond à la couche `Controller`.  

- `sn.sgp.utils`  
Contient quelques classes utilitaires.

- `sn.sgp.validators`  
Contient des classes faisant office de validateurs des formulaires de l'application.

## Notes  

A la racine du projet se trouve un script sql nommé `create_database.script.sql` permettant de créer une base de donnée nommée `db_sgp` ainsi que les tables permettant de stocker les informations des différentes entités de l'application.

### Entrée de l'application  

Aprés avoir lancé le script permettant de mettre en place la base de donnée et démarré l'application, vous vous rendrez au niveau de l'url suivante afin d'accéder à cette dernière :  
`http://localhost:8080/SeneGalleriePhoto/home`
