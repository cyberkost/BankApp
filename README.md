# Bank Application

## Objective

The main objective of this project is to create a robust backend microservice for banking operations without a frontend component. 
The microservice will expose a REST API to perform various banking transactions and manage customer accounts.

## Technologies

* Spring Boot
* JDK 20
* Hibernate and JPA
* PostgresSQL
* JUnit5 and Mockito
* Spring Security
* Docker
* Swagger
* SLF4J
* Maven
* Lombok

## Functionality

The microservice will implement CRUD operations for various entities related to banking operations. 
This includes:
Account Management: Creating, updating, and deleting customer accounts.
Transaction Processing: Performing transactions such as transfers between accounts.
Balance Inquiry: Retrieving account balances and transaction history.
Customer Management: Managing customer information and profiles.

## Security

The application will implement security measures to restrict access to authorized users. 
This will include requiring an "admin" username for accessing sensitive operations like CRUD functionality and performing POST, DELETE, and PUT requests.

