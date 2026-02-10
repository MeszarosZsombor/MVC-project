# MVC-project

## Description
This is a project about using Spring Boot + Hibernate + MySQL with Maven, running it inside a Docker. 

I want to deepen my skills and learn more about MVC architecture.
Spring Boot and Hibernate are new for me.
Once the project is finished/in a usable state I plan to deploy on AWS, which will be a new territory for me.

## Specifications
- **Java version:** 21 (corretto)
- **Spring boot version:** 3.5.6
- **Maven version:** 3.6.3
- **Architecture:** MVC

## Functionality
The project will be a pet shop, where the user can:
- register 
- login
- add new pet
- list pets even by categories
- new functions will be listed here while developing the project.

## Class diagram
![Class Diagram](https://github.com/MeszarosZsombor/MVC-project/blob/master/diagrams/Class_diagram.png)

## Database schema
TODO

## Sequence diagrams
TODO

## Testing
For testing, I will be using JUnit

Run all tests with:
```` bash
  mvn clean test
````

## Setup for personal use

### Prequisites
- Docker
- Maven (for local build)
- Java 21 (Corretto)

In the project's main folder run the following command:
```` bash
  docker build -t mvc-project .
````

After a successful build run you can run the following command:
```` bash
  docker run -p 8080:8080 mvc-project
````

You can access the page on http://localhost:8080.
