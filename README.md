# Blog REST service on Spring Boot

Test application to learn Spring Boot and REST services

## Run from console with mvn

```shell
$ mvn spring-boot:run
```

or

```shell
$ mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## Build and run

1. Run to create .jar file
    ```shell
   $ mvn clean package
    ```
2. Run to execute application
   ```shell
   $ java -jar target/blog-0.0.1-SNAPSHOT.jar com.nazarov.projects.blog.BlogApplication
   ```

## Generated swagger documentation

1. http://localhost:8090/blog/swagger-ui/index.html
2. http://localhost:8090/blog/v3/api-docs.yaml

## How to build postman collection from the generated OpenAPI documentation

1. https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-from-swagger/

# Frontend

Root folder contains "simple-ui" folder which contains simple frontend built on vanilla javascript
which consumes blog API. Just start the application on
with `mvn spring-boot:run -Dspring-boot.run.profiles=local` command, go to `simple-ui` folder and
open `index.html`