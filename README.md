# Etiqa Technical Assessment by Hafizzudin

[![Java Version](https://img.shields.io/badge/java-17-green.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot Version](https://img.shields.io/badge/spring--boot-3.2.1-green.svg)](https://spring.io/projects/spring-boot)

## Description

Spring Boot Application that to provides API Endpoints to manage Customers and Products.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Development](#development)
- [Swagger](#swagger)

## Features

- Find all customer
- Find customer by custemerId
- Add customer
- Update customer
- Delete customer
- Find all product
- Find product by productId
- Add product
- Update product
- Delete product


## Prerequisites

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [Lombok](https://projectlombok.org/) 
- [Postman](https://www.postman.com/api-platform/api-testing/)
- [Postgresql](https://www.postgresql.org/)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

1. Clone the repository:

    ```bash
    git clone https://github.com/mohdhafizzudin/etiqa.git
    ```

2. Build the project:

    ```bash
    cd your-repository
    mvn clean install
    ```

3. Run the application:

    ```bash
    java -jar target/your-artifact-id.jar
    ```

## Usage

Please refer [Swagger](http://localhost:8080/swagger-ui/index.html#/) for details.

## Development

The code can be divided into Controller, Servive, Repository, Config, Util, Properties.
- Controller - handling incoming HTTP requests
- Service - business logic of the application
- Repository - handling data access and persistence
- Config - handling error, swagger
- Util - contain utilies file
- Properties - contain centralize message file, and configuration

The code also include customize response and specification for easy usage.


## Swagger

Open API can be asses through the URL
http://localhost:8080/swagger-ui/index.html#/
