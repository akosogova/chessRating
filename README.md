# Chess rating tool
A tool for viewing the rating of chess players and changing the rating.
Tool works in two modes:
- `dev` - without auth
- `prod` - with auth

In "prod" mode, users with the MANAGER role can add, get and update player ratings.
Users with the USER role can only get list of users ratings and update their own rating. 

## Contributing

### Workflow
1. Git clone from `main` branch and create your own branch and work on it e.g.
```bash
git clone https://github.com/akosogova/chessRating.git
```
2. Rebase your branch if necessary, and push your code to [Github](https://github.com/akosogova/chessRating)
3. Create a pull request after manual verification of changes.
4. If the pull request is accepted, your branch will be merged into the main branch.
   If the pull request is rejected, please update your code per review comment and repeat step 2 to 4.

## Build

To build the module, do the following actions:
```bash
./gradlew build
```

## Usage
### Local build
**Prerequisites**
- docker

In case local build do the following actions:
1. Run mongodb docker on 27017 port
```bash
docker run --name mongodb -p 27017:27017 -d mongodb/mongodb-community-server:latest
```
2. Run Chess rating tool in `dev` or `prod` mode:
```bash
java -jar -Dspring.profiles.active=<mode> build/chessRating.jar
```
### Run via docker-compose
**Prerequisites**
- docker

1. Set SPRING_PROFILES_ACTIVE env variable in docker-compose.yml to `dev` or `prod`
2. Run:
```bash
docker compose up
```

## REST API
Request start endpoint: `http://127.0.0.1:8080/api/chess-rating/*`
1. `GET`  /players/rating - Get ratings of all players
2. `POST` /player - Add player
3. `PUT`  /player/rating/{id} - Update player rating with {id}  
4. `DELETE` /player/rating/{id} - Delete player with id {id}

## Requests examples
### Dev mode
#### Get ratings:
```bash
curl 'http://127.0.0.1:8090/api/chess-rating/players/rating'
```
#### Update player rating:
```bash
curl --request PUT 'http://127.0.0.1:8090/api/chess-rating/player/rating/22236a389-e2a3-41fd-80ec-f5965ba6cb96' --header 'Content-Type: application/json' --data '{"rating": "1750"}'
```
#### Add player:
```bash
curl 'http://127.0.0.1:8090/api/chess-rating/player' --header 'Content-Type: application/json' --data '{"id": "123345-e2a3-41fd-80ec-f5965ba6cb96","firstName": "User","lastName": "Test","email": "test.user@mail.com"}'
```

### Prod mode
#### Get access token
```bash
curl --location 'https://lemur-17.cloud-iam.com/auth/realms/chess-rating/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=chess-rating' \
--data-urlencode 'client_secret=kSXFfH2iTwxbWpvyP1YagQvDyCNHdTgK' \
--data-urlencode 'username=<username>' \
--data-urlencode 'password=<password>' \
--data-urlencode 'grant_type=password'
```
For example, get manager's access token:
```bash
curl --location 'https://lemur-17.cloud-iam.com/auth/realms/chess-rating/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=chess-rating' \
--data-urlencode 'client_secret=kSXFfH2iTwxbWpvyP1YagQvDyCNHdTgK' \
--data-urlencode 'username=test.manager@gmail.com' \
--data-urlencode 'password=manager' \
--data-urlencode 'grant_type=password'
```
Get user's access token:
```bash
curl --location 'https://lemur-17.cloud-iam.com/auth/realms/chess-rating/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=chess-rating' \
--data-urlencode 'client_secret=kSXFfH2iTwxbWpvyP1YagQvDyCNHdTgK' \
--data-urlencode 'username=test.user@gmail.com' \
--data-urlencode 'password=user' \
--data-urlencode 'grant_type=password'
```
#### Get ratings:
```bash
curl --location 'http://127.0.0.1:8090/api/chess-rating/players/rating' \
--header 'Authorization: Bearer <access_token>' 
```
#### Update player rating:
```bash
curl --request PUT 'http://127.0.0.1:8090/api/chess-rating/player/rating/22236a389-e2a3-41fd-80ec-f5965ba6cb96' --header 'Content-Type: application/json' --header 'Authorization: Bearer <access_token>' --data '{"rating": "1750"}'
```
#### Add player:
```bash
curl 'http://127.0.0.1:8090/api/chess-rating/player' --header 'Content-Type: application/json' --header 'Authorization: Bearer <access_token>' --data '{"id": "123345-e2a3-41fd-80ec-f5965ba6cb96","firstName": "User","lastName": "Test","email": "test.user@mail.com"}'
```