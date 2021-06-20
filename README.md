<h1>At this moment I started a refactoring in the develop-branch. Because of this, the project will be
not runnable next commits.</h1>

# deberts

Learn project for Java und Javascript. Own creation of the in CIS popular card game deberts. This
maven project consists of API-Part (Spring + Java) and frontend (Vue JS)

### Rules:

https://giftour.ru/en/contests-birthday/the-rules-of-the-debert-card-game-debertz/

### Actual ToDo's:

1. Refactoring
    1. User abstract class instead of Implementation
    2. Generate Deck on the Base of Cartesian Product
    3. Refactoring because of new Deck structure
2. Configure work with two docker images (game + database)

### Project setup

```
deberts
├─┬ backend     → backend module with Spring Boot code
│ ├── src
│ └── pom.xml
├─┬ frontend    → frontend module with Vue.js code
│ ├── src
│ └── pom.xml
└── pom.xml     → Maven parent pom managing both modules
```

### How to run?

```
mvn clean install
mvn --projects backend spring-boot:run
or 
docker-compose up :)
```
