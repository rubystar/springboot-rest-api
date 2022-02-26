
# Spring Rest Api(Employee Resource)
## Prerequisites
* Java 17
* maven-3.6.3
* MySQL 8.0.23

### DB Setup
* Create the db
* Update `application.properties` with db credentials
* Update flyway.conf
* Run flyway migrations
```
mvn clean flyway:migrate -Dflyway.configFiles=flyway.conf
```

### Run the app
```
mvn clean install
mvn spring-boot:run
```

### Run Tests
```
mvn test
```



