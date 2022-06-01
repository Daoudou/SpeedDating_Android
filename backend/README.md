# Esimed last TP
### Setup Docker Db
- [x] À partir du fichier `.env.example`, créez un fichier `.env` à la racine du projet.
- [x] Dans un terminal, démarrez la base de donnée avec la commande : `docker-compose up`.
- [x] Une fois qu'elle semble être démarrée, dans votre navigateur, rendez-vous sur http://localhost:8080.
- [x] Connectez-vous avec les informations saisies dans vos variables d'environnement.
  - [x] Le `Système` sera: `PostgreSQL`.
  - [x] Le `Serveur` sera: `postgres-svc`.
- [x] Si vous arrivez à vous connecter c'est que tout semble OK pour la suite.
### Setup project
- [x] Dans un nouveau terminal, installez les dépendances du projet: `npm install`.
- [x] La librairie choisie pour faire les appels à la DB est https://sequelize.org/v6/.
- [x] La première étape de ce TP, sera de remplacer les anciens "faux" appels à notre DB, par les nouveaux appels à travers la librairie `Sequelize`.
- [ ] Pour démarrer, dans le répertoire `models`, créez un fichier `user.model.js` qui devra contenir le schéma de création de notre table d'utilisateur. Inspirez vous de: https://sequelize.org/v6/manual/model-basics.html. Les contraintes sont les suivantes:
  - [ ] Un champ id, qui aura la config: Clé Primaire / Type = UUID / Valeur par défaut = UUIDV4.
  - [ ] Un champ firstName, qui aura la config: Type = string / Requis / Unique.
  - [ ] Un champ lastName, qui aura la config: Type = string / Requis.
  - [ ] Un champ password, qui aura la config: Type = string / Requis.
- [ ] Une fois ce model créé vous devrez vous en servir comme indiqué dans la doc de la librairie, pour remplacer les anciens appels.
> Précision: Dans la classe `WebServer`, une méthode a été ajoutée pour synchroniser la DB. Cette méthode est lancée au démarrage du serveur web. Elle permet, à partir des models de DB créés dans notre code, de générer les bonnes tables en DB.
>
> Ainsi, si vous vous trompez dans la définition de votre model, il se peut qu'il faille aller dans `Adminer` (http://localhost:8080), pour supprimer la table, de façon à ce que le serveur la recréé au démarrage. Gérez cela en fonction de votre besoin.