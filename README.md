# deberts

Learn project for Java und Javascript. Own creation of the in CIS popular card game deberts. This maven project consists
of API-Part (Spring + Java) and frontend (Vue JS)

### Rules:

https://giftour.ru/en/contests-birthday/the-rules-of-the-debert-card-game-debertz/

### Actual ToDo's:

1. Create Docker for PostgreSQL
2. Create tables to enable save option
3. Create an option to save the game
4. Configure work with two docker images (game + database)

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
```
