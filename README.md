# Inventory Optimization App

This project is an Inventory Optimization application built with a Vue.js frontend and a Spring Boot backend. The application helps in optimizing inventory by calculating purchase schedules based on various parameters.

[Technical Interview Instructions](https://docs.google.com/document/d/1JrymuUlePo70TVO0uUy8um-2Q8GT6_ed-iyAcnnnMec/edit?usp=sharing)

## Result Test

The best package format multiple of 2 superior of 4 is 6.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Clone the repository](#clone-the-repository)
- [Frontend](#frontend)
  - [Project Setup](#project-setup)
  - [Environment Variables](#environment-variables)
  - [Run Unit Tests](#run-unit-tests)
  - [Run End-to-End Tests](#run-end-to-end-tests)
- [Database](#database)
  - [Database Setup](#database-setup)
- [Backend](#backend)
  - [Project Setup](#project-setup-1)
  - [Environment Variables](#environment-variables-1)
  - [Run the Application](#run-the-application)
  - [Run Tests](#run-tests)

## Features

### 1. Data Management

- Tracks daily yogurt consumption based on predefined patterns.
- Defines a fixed purchase day (Sunday) and a fixed delivery delay (2 days).
- Supports customizable yogurt pack sizes (default: 2 yogurts per pack).

### 2. Optimal Purchase Calculation

- Computes the optimal yogurt purchase quantities starting from January 5, 2025, for a duration of one year.
- Ensures no stock shortage while minimizing excess inventory.

### 3. User Interface

- Allows modification of delivery delay and weekly consumption profile.
- Displays purchase schedules in a tabular format.
- Provides a graphical representation of daily stock levels.

### 4. Alternative Pack Size Optimization

- Tests larger pack sizes (above 4) to find an optimal balance between stock levels and replenishment frequency.

## Technology Stack

- **Frontend**: Vue.js (TypeScript)
- **Backend**: Java 17 Spring Boot

## Project Structure

The project is a mono repository including a backend and a frontend folder.

## Clone the repository

```
git clone https://github.com/Srizza93/inventory-optimisation.git
cd inventory-optimisation
```

# Frontend

## Project Setup

node version: v20.14.0

```
cd frontend
npm install
npm run dev
```

## Environment Variables

Set up a .env file at the root of the frontend.

- VITE_APP_PARAMETERS_ID is used to stock the parameters id from the database, as actually there is no authentication.

```
VITE_API_URL
VITE_APP_PARAMETERS_ID
```

## Run Unit Tests

```
npm run test:unit
```

## Run End-to-End Tests

```
npm run test:e2e:dev
```

# Database

PostgreSQL

## Database Setup

1. **Install PostgreSQL**: If you don't have PostgreSQL installed, download and install it from [here](https://www.postgresql.org/download/).

2. **Create a Database**: Open the PostgreSQL shell and create a new database.

   ```
   CREATE DATABASE inventory_db;
   ```

3. **Configure Database Connection**: Update the `application.properties` file in the [resources](http://_vscodecontentref_/0) directory with your database connection details.

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

4. **Run Database Migrations**: If you are using a tool like Flyway or Liquibase for database migrations, run the migrations to set up the initial schema.

   ```
   ./mvnw flyway:migrate
   ```

Make sure to replace `your_username` and `your_password` with your actual PostgreSQL username and password.

# Backend

## Project Setup

Run Docker Engine

```
cd backend
./mvnw clean install
```

Set JDK 17 as the default version: You may need to set JDK 17 as the default version. Add the following to your shell profile (~/.bash_profile, ~/.zshrc, etc.):

```
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH
```

Note for IntelliJ Users: If you are using IntelliJ IDEA, you can follow its instructions to set up JDK 17. IntelliJ will guide you through the process of configuring the JDK for your project.

## Environment Variables

Set up a local.env file at the root of the backend.

```
DB_USER
DB_PASSWORD
DB_URL
CORS_ALLOWED_ORIGIN
```

## Run the Application

```
cd backend
./mvnw spring-boot:run
```

## Run Tests

```
cd backend
./mvnw test
```
