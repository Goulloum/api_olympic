
# OlympicAPI

Membre projet JEE : Wassim FAYALA, Mathieu GUILLEMIN

API REST faites dans le cadre d'un projet EFREI en Spring boot




## Utilisation avec Docker

```bash
docker-compose up --build -d
```

## Utilisation avec Podman

```bash
podman-compose up --build -d
```



## Intéragir

API => http://locahost:8080

Documentation Swagger => http://localhost:8080/swagger-ui/index.html

## Utilisateur de base

De base, deux utilisateurs sont créé par un Seeder:

User:

- email: user@test.com
- password: user

Admin:

- email: admin@test.com
- password: admin



## Récupération du token

#### Login

```http
  GET /auth/login
```

| Paramètre | Type     | Valeur               |
| :-------- | :------- | :------------------------- |
| `email` | `string` | admin@test.com |
| `password` | `string` | admin |

Une fois le token récupérer:

- Swagger: Entrer le dans l'icone authorize en haut à droite de la page

- Manuellement: L'insérer dans le header Authorization: Bearer {{Votre token}}

