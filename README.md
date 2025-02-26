# inventory-optimisation

# Inventory Optimization App

This project is an Inventory Optimization application built with a Vue.js frontend and a Spring Boot backend. The application helps in optimizing inventory by calculating purchase schedules based on various parameters.

[Technical Interview Instructions](https://docs.google.com/document/d/1JrymuUlePo70TVO0uUy8um-2Q8GT6_ed-iyAcnnnMec/edit?usp=sharing)

## Table of Contents

- [Project Structure](#project-structure)
- [Clone the repository](#clone-the-repository)
- [Features](#features)
- [Frontend](#frontend)
  - [Project Setup](#project-setup)
  - [Environment Variables](#environment-variables)
  - [Run Unit Tests](#run-unit-tests)
  - [Run End-to-End Tests](#run-end-to-end-tests)
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

- Computes the optimal yogurt purchase quantities starting from January 6, 2025, for a duration of one year.
- Ensures no stock shortage while minimizing excess inventory.

### 3. User Interface

- Allows modification of delivery delay and weekly consumption profile.
- Displays purchase schedules in a tabular format.
- Provides a graphical representation of daily stock levels.

### 4. Alternative Pack Size Optimization

- Tests larger pack sizes (above 4) to find an optimal balance between stock levels and replenishment frequency.

## Technology Stack

- **Frontend**: Vue.js (TypeScript)
- **Backend**: Java Spring Boot

## Project Structure

The project is a mono repository including a backend and a frontend folder.

## Clone the repository

```
git clone https://github.com/Srizza93/inventory-optimisation.git
```

# Frontend

## Project Setup

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

# Backend

## Project Setup

```
cd backend
mvn clean install
```

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
mvn spring-boot:run
```

## Run Tests

```
cd backend
mvn test
```
