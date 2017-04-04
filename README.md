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


If You want to do some operations on the database just use e.g:

https://spring.io/guides/gs/accessing-data-mongodb/



