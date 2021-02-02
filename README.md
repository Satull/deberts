# deberts

Learn project for Java und Javascript. Own creation of the in CIS popular card game deberts. This maven project consists
of API-Part (Spring + Java) and frontend (Vue JS)

### Rules:

https://giftour.ru/en/contests-birthday/the-rules-of-the-debert-card-game-debertz/

### Actual ToDo's:

1. Docker
2. Refactoring of the card storage to the cartesian product
3. Card combinations
4. ...

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
