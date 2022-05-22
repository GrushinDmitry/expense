# Expense accouting service

## Description

The service allows:
- create, delete, edit and search for a person who keeps track of their spending;
- when creating a new person, a check is made for the existence of the created person in the database to eliminate duplicates. If the person exists, an appropriate warning is issued;
- if there is no searched person in the database, an error is generated that no person with the specified first and last name were found;
- if the database is empty, the request for all persons will return an error that there are no persons;
- create, delete, edit and search for an expense category;
- when creating a new category, a check is made for existence in the database to exclude duplicates. If the category exists, an appropriate warning is issued;
- if the required category is not found in the database, an error is generated that the category with the given name was not found;
  -if the database is empty, the request for all categories will return an error that there are no categories;

### Technical Info

* Technologies:
    * Kotlin
    * Spring Boot
    * Spring Webflux
    * PostgreSQL R2DBC
    * Flyway Migration
    * Gradle
* Database:
    * PostgreSQL

