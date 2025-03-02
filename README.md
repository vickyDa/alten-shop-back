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

Wishlist (prérequis : créer un utilisateur, l'authentifier, créer un produit)
######################################################################################"

ajouter un produit à la wishlist 
POST  >> http://localhost:8090/wishlist/1/add?productId=1
variable 1 >> userId
Variable 2 >> productId

récupérer la wishlist d'un utilisateur
GET >> http://localhost:8090/wishlist/1
Variable >> userId

Retirer un produit de la wishlist
DELETE >> http://localhost:8090/wishlist/1/remove?productId=1
Variable 1 >> userId
Variable 2 >> productId


Gestion du panier (prérequis : créer un utilisateur, l'authentifier, créer un produit)
###############################################################################################

Ajouter un produit à un panier pour un utilisateur
http://localhost:8090/cart/1/add/1?quantity=1
Variable 1 >> userId
Variable 2 >> productId
Variable 3 >> quantity

Récupérer le panier d'un utilisateur
GET >> http://localhost:8090/cart/1
Variable >> userId

Mettre à jour un produit du panier d'un utilisateur
PUT >> http://localhost:8090/cart/1/update/1?quantity=2
Variable 1 >> userId
Variable 2 >> productId
Variable 3 >> quantity

Retirer un produit du panier d'un utilisateur
DELTE >> http://localhost:8090/cart/1/remove/1
Variable 1 >> userId
Variable 2 >> productId

Séquence de test proposé

Créer un utilisateur admin
Authentifier l'utilisateur
Récupérer la liste des produits qui sera vide au départ(sur swagger il vous sera demandé de vous authentifier avec le mail et le mot de passe d'un utilisateur créé, sur postman renseigner le token dans les paramètre authorization)
Ajouter un produit avec un utilisateur non admin : attendu 401 unauthorized
Ajouter un produit avec un utilisateur avec le mail admin@admin.com : attendu le produit est bien ajouté
Récupérer à nouveau la liste des produits : attendu le poduit créé apparait bien dans la liste
Modifier le produit avec un utilisateur non admin (email différent de admin@admin.com) : attendu 401 unauthorized
Modifier le produit avec un utilisateur admin (email = admin@admin.com) : attendu le produit est bien modifié
Ajouter le produit au panier de l'utilisateur courant
Récupérer le panier de l'utilisateur courant : attendu les produits ajoutés apparaissent dans le panier de l'utilisateur
Modifier la quantité du produit dans le panier
Ajouter un nouveau produit au panier
Récupérer à nouveau le panier : tous les produits ajoutés sont présents
Supprimer un produit du panier
Récupérer à nouveau le panier : l'élément supprimé n'y es plus
Supprimer le panier
Récupérer à nouveau le panier : les items sont vides dans le panier
Ajouter le produit à la wishist
Récupérer a liste des produits présent dans la wishlist
Supprimer la wishlist
Récupérer à nouveau la wishlist : le tableau des produits y est vide

