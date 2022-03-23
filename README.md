# 🛁🔥 Saunah Backend
Backend for Saunah Management App.

This application provides a REST-API for the Saunah management app.

## 👨🏼‍💻 Technology Stack
The application is based on [Spring Boot](https://spring.io/projects/spring-boot).

## 🖥 Running the application
The application can be started by running `./gradlew bootRun` inside the project directory from the command line, or by running `SaunahBackendApplication.java` from your IDE.

It will then become available at [`localhost:8080`](http://localhost:8080)

## 🛠 IDE Configuration

Running the application has been tested with both IntelliJ IDEA and VS Code.

Make sure to configure your IDE to use Java 11 or later.

For VS Code, there is a set of recommended extensions for developing with java, found in `.vscode/extensions.json`. VS Code should automatically prompt for installing the recommended extensions when first opening the project.

## 🐋 Installing Docker Desktop

Download Docker Desktop on the [`Docker Website`](https://docs.docker.com/get-docker/) and install it.

To run docker on Windows you need to install WSL 2 and set it as default.

## 💾 Running Database on Docker

Then you can execute the following comand to run the database `docker run -p 5556:5432 --name saunah-db -e POSTGRES_PASSWORD=saunah -e POSTGRES_USER=saunah -e POSTGRES_DB=saunah -d postgres:latest`
