# Rest-with-spring-boot-and-java-erudio

## Separate course objectives in each module

### configure environment

- Install and configure all the tools used in the course.

### configuring project

- Configure and download dependencies used in the project using SpringBoot 3 in conjunction with the IDE.

### Getting Started with Spring Boot 3

- First steps in SpringBoot 3 such as understanding how Pom.xml and Maven works and creating the first endpoints.

## Understanding how Path parms work and exception handling

- Learning to define application logic, validation methods, understanding annotations like @RestController @Requestmapping, @PathVariable and exception handling with @ControllerAdvice, @ReponseStatus and ExceptionHandle.

### How to use is HTTP verbs (GET, POST, PUT AND DELETE)

- Learning when and how to use HTTP verbs.

### Integrating the API to the Database

- Creating database, setting connection properties.

- Inserting annotations that reference the entities in the database with @entity @colum and @repository.

- Removing mocks and updating the create, update, delete and find methods to work together with the database.

- Refactoring person controller.

- Refactoring repository interface.

### Object value design pattern

- Add Dozzer mapper in the project.

- Creating unit tests for test the Dozzer mapper.

- Refactoring services to work with VO(value object) product.

### Adding support for migrations with flyway

- Adding migrations to the project.

- Migrations with prompt and flyway.

### Content Negotiation

- Implementing Content Negotiation with QUERY Parameter.

- Implementing Content Negotiation with HEADER Parameter.

- Serialize in YML.

### Implementing HATEOAS

- Adding Represetation model.

- Converting the mapper to use the VO's key attribute as the model id.

- Implementing HATEOAS in API operations.

- Using Mockito for testing with Junit5.

- Create test case for create and update with null object as parameter.

- implementing test in the find all method using mock entity list.

### Adding Swagger Support

- Swagger is a farmwork with support for most existing programming languages, it helps developers define, create, document and consume RESTFull services.

- Adding Swagger in application.

- Defining basic Swagger settings.

- Completing challenge to create new model, VO, services, repositories and controls for new book entity.

- Creating tests and mocks for new entity Book.

- Configure test containers.

- Implementing intregation test for swagger page.

- mporting collection into postman through swagger.

### Config CORS

- Enabling CORS in the controller in one or more different origins.

- Configuring CORS globally.

- Creating integration test.

### Authentication With JWT and Spring Security

- Adding migrations for table creation and populating permission, user and user permission.

- Creating a model with table permission and user with join table.

- Creating a repository fort users.

- Creting user services.

- Understanding the usage to anotations @ManyToMany @JoinTable and Query.

- Understanding how to use JPQL language to manipulate objects that represent entities in the database.

- Creting handles and excptions for authentication.

- Creating VOs for tocken.

- Creating token and defining refresh token.

- Defining token algorithm.

- Creating method for resolve token.

- Creating method for validate token.

- Creating method JwtTokenFilter and JwtConfigurer.

- Implementing Security Config.

- Learning how to generate encrypted password.

- Defining parameters for use in postman.

### Securing Api With Rest Assured Test Container And JUnit5

- Fixing broken tests and adding token and accont credentials VO.

- Creating AuthControllerTests With Json.

- Creating AuthControllerTests With Xml.

- Creating custom YMLMapper.

- Creating AuthControllerTests With Yaml.

- Separating cors tests from authentication and controller tests

- Creating PersonControllerJsonTest
