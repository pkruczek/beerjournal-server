# Beer Journal
An application for storing Beer achievements

## Run
* run application:

```mvn clean spring-boot:run```

## Tests
Project has [Spock Framework](http://spockframework.org/) included

run tests:

```mvn clean test```

For integration tests you can use [Embedded Mongo](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo) 
which is added to test scope

## Mongo usage
Database is placed on vps server and application is automatically connected to it via application.properties  

LOCAL DEV

If you want to change it for local development with local database you need to set property to localhost

```
spring.data.mongodb.host=localhost
```
If you are Linux user and use docker you can perform these steps in analogous way in order to install docker, create database "beer-journal" and user with password specified in dev-properties  

#### install docker, pull and run mongo image
```
sudo apt install docker.io
docker pull mongo:latest
docker run --name mongo-bj -d -p 27017:27017 mongo --auth
```

#### run mongo shell
```
docker exec -it mongo-bj mongo admin
```

#### create database for beer journal
```
use beer-journal
```

#### set up user and grant roles (much security wow)
```
use admin
db.createUser({ user: 'bj-janusz', pwd: 'bubak', roles: [{ role: "userAdminAnyDatabase", db: "admin" }]});
db.grantRolesToUser("bj-janusz", ["readWrite", {role: "readWrite", db: "beer-journal"}]);
```

#### authenticate in shell
```
db.auth('bj-janusz', 'bubak');
```

#### run mongo with authentication if you want to perform some kind of action in shell
```
docker exec -it mongo-bj mongo admin -u "bj-janusz" -p "bubak" --authenticationDatabase "admin"
```


## MONGODB (MongoProperties)
```
spring.data.mongodb.authentication-database= # Authentication database name.
spring.data.mongodb.database=test # Database name.
spring.data.mongodb.field-naming-strategy= # Fully qualified name of the FieldNamingStrategy to use.
spring.data.mongodb.grid-fs-database= # GridFS database name.
spring.data.mongodb.host=localhost # Mongo server host. Cannot be set with uri.
spring.data.mongodb.password= # Login password of the mongo server. Cannot be set with uri.
spring.data.mongodb.port=27017 # Mongo server port. Cannot be set with uri.
spring.data.mongodb.repositories.enabled=true # Enable Mongo repositories.
spring.data.mongodb.uri=mongodb://localhost/test # Mongo database URI. Cannot be set with host, port and credentials.
spring.data.mongodb.username= # Login user of the mongo server. Cannot be set with uri.
```
If You want to do some operations on the database just use e.g:

https://spring.io/guides/gs/accessing-data-mongodb/



