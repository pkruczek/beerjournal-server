## Beer Journal

An application for storing Beer achievements

### Run

* run MongoDB with default parameters (port: ```27017```), e.g. with Docker

```docker run -p 27017:27017 mongo```
* run application:

```mvn clean spring-boot:run```

### Tests
Project has [Spock Framework](http://spockframework.org/) included

run tests:

```mvn clean test```

For integration tests you can use [Embedded Mongo](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo) 
which is added to test scope

### Mongo usage

Define connection parameters in application.properties file


by default in application.properties we set

spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.repositories.enabled=true

but we can also set another properties :

# MONGODB (MongoProperties)
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

If You want to do some operations on the database just use e.g:

https://spring.io/guides/gs/accessing-data-mongodb/



