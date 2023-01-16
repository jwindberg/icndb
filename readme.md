Spring Boot demo application
loads data into h2 database on startup
makes data available via spring spring-data-rest, graphql, & regular rest controllers.

graph ql ui: http://localhost:7070/graphiql

spring rest data: http://localhost:7070/api

spring rest controllers:

http://localhost:7070/jokes[?category=xx&category=yy...]

http://localhost:7070/joke/{xxx}

http://localhost:7070/joke/random

http://localhost:7070/categories

http://localhost:7070/category/{xxx}