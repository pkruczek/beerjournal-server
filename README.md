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

mongodb.databaseName  - database name
mongodb.databaseHost  - host where database is running
mongodb.databasePort=27017  - by default
mongodb.isDatabaseAuthenticated = false  - if database have authentication set on true
mongodb.authenticatedDatabaseName= - nome of the database where we storage users and their roles
mongodb.userName= - user name for authenticate to the database
mongodb.password= - password for above user name

If You want to do some operations on the database just use e.g:

    @Autowired
    private MongoDatabase database;

    database.getCollections("collectionName")

for more read http://mongodb.github.io/mongo-java-driver/3.4/javadoc/



