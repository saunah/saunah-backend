# 🛁🔥 SauNah Backend

[![CI](https://github.com/saunah/saunah-backend/actions/workflows/run-deployment.yml/badge.svg?branch=main)](https://github.com/saunah/saunah-backend/actions/workflows/run-deployment.yml)


Backend for Saunah Management App.

This application provides a REST-API for the Saunah management app.

## 🎛 Technology Stack
The application is based on [Spring Boot](https://spring.io/projects/spring-boot).

## 👨🏼‍💻 Running the Application
The application can be started by running `./gradlew bootRun` inside the project directory from the command line, or by running `SaunahBackendApplication.java` from your IDE.

It will then become available at [`http://localhost:8080`](http://localhost:8080)

## 🛠 IDE Configuration

Running the application has been tested with both IntelliJ IDEA and VS Code.

Make sure to configure your IDE to use Java 11 or later.

For VS Code, there is a set of recommended extensions for developing with java, found in `.vscode/extensions.json`. VS Code should automatically prompt for installing the recommended extensions when first opening the project.

### 🌱 Setting Environment Variables

Environment specific values for the application are set via environment variables, which allows to configure certain values (database hostname, password, etc.) differently, depending on the environment the application is running on (eg. local, staging, production).

In order to configure your IDE to run the application with environment variabeles set which contain configuration values for local development, please set the following environment variables on your system:

- `SAUNAH_DB_HOST=127.0.0.1`
- `SAUNAH_DB_PORT=5556`
- `SAUNAH_DB_USER=saunah`
- `SAUNAH_DB_PASSWORD=saunah`
- `SAUNAH_DB_NAME=saunah`
- `SAUNAH_FRONTEND_BASE_URL=localhost:3000`

**To configure IntelliJ to use these values**, navigate to `Run → Edit Configurations`, make sure `SaunahBackendApplication` is selected on the left hand side, and enter the following string to *Environment variables*: `SAUNAH_DB_HOST=127.0.0.1;SAUNAH_DB_PORT=5556;SAUNAH_DB_USER=saunah;SAUNAH_DB_PASSWORD=saunah;SAUNAH_DB_NAME=saunah;SAUNAH_FRONTEND_BASE_URL=localhost:3000`.

Please make sure there is no leading or trailing space in the string, as this might cause errors otherwiese.

## 💾 Setting up a Development Database

### 🐋 Installing Docker Desktop

To run a database for local development, it is recommended that you connect the application to a database running in a Docker Container.

To get Docker Desktop, download the application on the [`Docker Website`](https://docs.docker.com/get-docker/) and install it.

To run docker on Windows you need to install WSL 2 and set it as default.

### 🚢 Running Database on Docker

Once you have installed Docker Desktop, you can execute the following comand to run a PostgreSQL database locally for development: `docker run -p 5556:5432 --name saunah-db -e POSTGRES_PASSWORD=saunah -e POSTGRES_USER=saunah -e POSTGRES_DB=saunah -d postgres:latest`

This will make the database accessible on `localhost` (or `127.0.0.1`) on port `5556`. Username, passwort and database name are all set to `saunah`.


## 🤝🏼 Contributing

If you'd like to contribute take a look at our [Contribution Guidelines](docs/CONTRIBUTING.md)


## 🚀 Deployment of the Application

Continuous Integration / Delivery of the application is done using Github Actions.

On every push, the project will be built and test will be run inside the build pipeline. The outcome can be shown in the Github Actions tab on [github.com](https://github.com/saunah/saunah-backend/actions).

On pushes to the `main` branch (eg. via Pull-Request), the application will be packaged as a Docker Container and uploaded to the GitHub Package Registry ([ghcr.io](https://ghcr.io)), in addition to running the tests. The uploaded container will have the tag of the commit SHA which triggered the build (eg. `efdd3f4`).

After that, an update for the deployment of the application on the staging Kubernetes environemnt is triggered, which makes the application available for testing there after the build pipeline ran successfully (usually within a few minutes). It can be accessed via [https://saunah-backend-staging.k8s.init-lab.ch](https://saunah-backend-staging.k8s.init-lab.ch).

If a tag is added to a commit which starts with `v`, the application is being deployed to the production Kubernetes environment. Please make sure that tags are only set on the `main` branch and only if it has been successfully tested on the staging environment. Version numbers should follow [Semantic Versioning](https://semver.org/). The production backend is available at [https://saunah-backend-prod.k8s.init-lab.ch](https://saunah-backend-prod.k8s.init-lab.ch).


### 🔐 Deployment of the Database and Secrets

While the backend application is deployed on every push to the `main` branch, deployment of secrets and the database are not done automatically on each push, as a new deployment on each push is not needed, because those deployments usually do not change with a release.

If a new Kubernetes environment needs to be set up, however, there are scripts in the repository to do so:

1. Deploy the secrets to the namespace. To do so, copy the file in `helm/secrets-template.yaml` to `helm/secrets.yaml` and add the corresponding secret values to the file. (`helm/secrets.yaml` or any other secrets must never be commited to git, for obvious reasons!)
2. Deploy the secrets to the cluster using `kubectl apply -f helm/secrets.yaml`.
3. Deploy the database to the cluster using `helm install -f helm/saunah-database/values-staging.yaml saunah-database helm/saunah-database`. (Or use a different vlaues file, depending on the target of your deployment.) Make sure that the database depends on the secrets set in step 1. and 2., thus is need to be executed after these steps.


## 👌🏼 Definition of Done
The Definition of Done is automatically applied as the pull-request template. It can be found in [docs/pull_request_template.md](./docs/pull_request_template.md).


## 📚 Further Documentation

Further documentation can be found in [docs/README.md](./docs/README.md).
