Cloner à partir de la branche master
Exécuter le projet (springboot avec une base H2)

Plan de test des API

##############################################################


Démarrer l'application Back disponible sur le port 8090 par défaut >> http://localhost:8090

Test possible via Postman ou Swagger
#######################################

Postman
##############

- Créer un utilisateur admin (email : admin@admin.com) via postman
Exemple : 
POST : http://localhost:8090/auth/account
{
  "username": "admin",
  "firstname": "admin",
  "email": "admin@admin.com",
  "password": "alten1234"
}
- Créer un autre utilisateur avec un email différent de admin@admin.com
- Tester la connexion avec un utilisateur créé 
  Exemple :
  POST : http:://localhost:8090/auth/token
  {
  "email": "admin@admin.com",
  "password": "alten1234"
  }
- Récupérer le token généré

Utiliser le token pour tester les autres API 

- Ajouter un ou plusieurs produits en utilisant le format JSON adapté voir exemple format plus bas
- Seul l'utilisateur admin avec un mail admin@admin.com peut ajouter des produits, modifier des produits ou supprimer des produits
   > Créer un autre utilisateur avec un email différent de admin@admin.com , se connecter , utiliser le token pour la création produit afin de  valider ce principe
   > On obtient un code 401 : Unauthorized pour tous les utilisateurs sauf celui avec un email admin@admin.com
- Si utilisateur admin tester la modification produit, la suppression produit
- Vérifier que tous les utilisateurs ont accès à l'API de listing des produits et récupération des détails produits via leur token

Test Via Swagger
##########################################################
Exposer les APIs disponible en accédant au lien swagger 
http://localhost:8090/swagger-ui/index.html#/

- Créer un ou plusieurs utilisateur dont l'admin avec l'email admin@admin.com
Exemple 
login : admin@admin.com
password : alten1234
Voir format JSON dans l'exemple POSTMAN

Tester les connexions avec les utilisateurs crées
POST : http://localhost:8090/auth/token
Voir format JSON dans l'exemple POSTMAN

NB: il vous sera demandé de vous authentifié avec un utilisateur pour exécuter  tous les api qui necessitent une authentification

- Lister les produits 
  Exemple  >> GET : http://localhost:8090/products

- Récupérer les détails d'un produits
  Exemple >> GET: http://localhost:8090/products/1


- Créer ou ajouter des produits
POST : http://localhost:8090/products
Exemple JSON 
{
  "code": "PRD001",
  "name": "Nom du produit",
  "description": "Description détaillée du produit",
  "image": "https://exemple.com/image-produit.jpg",
  "category": "Catégorie du produit",
  "price": 99.99,
  "quantity": 50,
  "internalReference": "REF123456",
  "shellId": 10,
  "inventoryStatus": "INSTOCK",
  "rating": 4.5
}

- Supprimer un produit
  Exemple  >> DELETE http://localhost:8090/products/1

- Modifier un produit
  Exemple >> PATCH : http://localhost:8090/products/1
  JSON
  {
    "code": "PRD001NEW",
    "name": "Nouveau Nom Produit NEW",
    "description": "Description détaillée du produit NEW",
    "image": "https://exemple.com/image-produit.jpg",
    "category": "Accessoire",
    "price": 99.99,
    "quantity": 50,
    "internalReference": "REF123456",
    "shellId": 10,
    "inventoryStatus": "INSTOCK",
    "rating": 4.5,
    "createdAt": "2025-03-01T19:11:59.931576",
    "updatedAt": null
  }
  
NB: seul l'utilisateur avec un email admin@admin.com peut exécuter avec succès les 3 APIs ci dessus

Tester les API de gestion de panier et favoris avec un utilisateur créé et authentifié que ce soit sur postman ou swagger



