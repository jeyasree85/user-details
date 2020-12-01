### Requirements
JDK 11.0,
Maven 3.6.0,
postgres Sql.

### Running the application locally

Execute queries given in file postgres_query in postgres Database to create Database and required table.

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the UserDetailsApplication class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

```bash
mvn spring-boot:run
```

### User API 

User API will be available in the swagger link Url http://localhost:9093/swagger-ui.html#/user-controller

This API includes CRUD operations for the user.
Here I have enclosed SwaggerUserApi.png file as swagger image for reference.


