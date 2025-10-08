# Payment Service Application
This is a Spring Boot based payment service application with REST APIs to manage payments and associated accounts.

## Features
- Create and validate payments with associated account details
- Query payments with filters on currency and minimum amount
- Validation exceptions and error handling
- JPA entities and repositories for persistence
- Unit tests covering controller endpoints

## Prerequisites
- Java 17 or above installed
- Apache Maven 3.6+ installed
- Internet connection to download dependencies

## Building the project
1. Clone or download the source code.
2. Open a terminal at the project root directory (where `pom.xml` is located).
3. Run the following Maven command to compile, test, and package the application into an executable jar:
   _mvn clean package_ 
4. This will produce a jar file with dependencies in the `target` directory, named something like:
   _payment-service-1.0.0-SNAPSHOT.jar_


## Running the application

Run the jar with the following command or use mvn spring-boot:run to run the application
java -jar target/payment-service-1.0.0-SNAPSHOT.jar

The application will start an embedded server (default port 8080), exposing REST endpoints for payments.

## API endpoints

- `POST /http://<host>:<port>/payments` - Create a new payment
- Ex POST Request: http://localhost:8080/payments
- POST Request Body Ex:
  {
    "currency": "INR",
    "amount": 500.00,
    "counterparty": {
        "type": "SORT_CODE_ACCOUNT_NUMBER",
        "accountNumber": "12345678",
        "sortCode": "123456"
    }
   }

- Following are the GET APIs Query payments with optional filters (`currencies`, `minAmount`)
- Ex GET Request: http://localhost:8080/payments
- `GET /http://<host>:<port>/payments` - Query all payments
- `GET /http://<host>:<port>/payments?currencies=USD, GBP` - Query payments with currencies filter 
- `GET /http://<host>:<port>/payments?payments?minAmount=101` - Query payments with minAmount filter
- `GET /http://<host>:<port>/payments?currencies=GBP&minAmount=100` - Query payments with currencies & minAmount filter

## H2 DB 
- Once the service is up and running connect with the H2 DB using the following 
- URL: http://localhost:8080/h2-console
- It will prompt the H2 console and provide the following params to connect with H2
  - JDBC URL:jdbc:h2:mem:paymentdb
  - User Name: sa 
  - Password: password

