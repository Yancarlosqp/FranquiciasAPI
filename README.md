# Franquicias API

REST API for managing franchises, branches, and products built with Spring Boot WebFlux and MongoDB.

## Table of Contents
- [System Requirements](#system-requirements)
- [Local Setup](#local-setup)
- [API Documentation](#api-documentation)
  - [Franchise Endpoints](#franchise-endpoints)
  - [Branch Endpoints](#branch-endpoints)
  - [Product Endpoints](#product-endpoints)
- [Project Architecture](#project-architecture)

## System Requirements

- Java Development Kit (JDK) 17
- Maven 3.9+
- MongoDB instance (or MongoDB Atlas account)

## Local Setup

1. Clone the repository:
   ```bash
   git clone https://your-repository-url/franquicias-api.git
   cd franquicias-api
   ```

2. Configure MongoDB connection:
   
   Edit the `src/main/resources/application.properties` file to update the MongoDB connection string:
   ```properties
   spring.data.mongodb.uri=mongodb+srv://your-username:your-password@your-cluster-url/franquiciasdb?retryWrites=true&w=majority
   ```

3. Build the project:
   ```bash
   ./mvnw clean install
   ```
   
4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   
5. Access the API:
   - Base URL: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html

## API Documentation

### Franchise Endpoints

#### Create a Franchise
- **URL:** `/franchises`
- **Method:** `POST`
- **Description:** Creates a new franchise
- **Request Body:**
  ```json
  {
    "name": "Franchise Name"
  }
  ```
- **Response:** Returns the created franchise object with its ID

#### Get a Franchise
- **URL:** `/franchises/{id}`
- **Method:** `GET`
- **Description:** Retrieves a franchise by its ID
- **Response:** Returns the franchise object with its branches and products

#### List All Franchises
- **URL:** `/franchises`
- **Method:** `GET`
- **Description:** Lists all franchises
- **Response:** Returns an array of franchise objects

#### Update Franchise Name
- **URL:** `/franchises/{franchiseId}/name`
- **Method:** `PUT`
- **Description:** Updates the name of a franchise
- **Request Body:**
  ```json
  {
    "name": "New Franchise Name"
  }
  ```
- **Response:** Returns the updated franchise object

### Branch Endpoints

#### Add Branch to Franchise
- **URL:** `/franchises/{franchiseId}/branches`
- **Method:** `POST`
- **Description:** Creates a new branch and adds it to a franchise
- **Request Body:**
  ```json
  {
    "name": "Branch Name"
  }
  ```
- **Response:** Returns the created branch object with its ID

#### Update Branch Name
- **URL:** `/franchises/{franchiseId}/branches/branches/{branchId}/name`
- **Method:** `PUT`
- **Description:** Updates the name of a branch
- **Request Body:**
  ```json
  {
    "name": "New Branch Name"
  }
  ```
- **Response:** Returns the updated branch object

### Product Endpoints

#### Add Product to Branch
- **URL:** `/branches/{branchId}/products`
- **Method:** `POST`
- **Description:** Creates a new product and adds it to a branch
- **Request Body:**
  ```json
  {
    "name": "Product Name",
    "stock": 100
  }
  ```
- **Response:** Returns the created product object with its ID

#### Remove Product from Branch
- **URL:** `/branches/{branchId}/products/{productId}`
- **Method:** `DELETE`
- **Description:** Removes a product from a branch
- **Response:** No content (204) if successful

#### Update Product Stock
- **URL:** `/branches/{branchId}/products/{productId}/stock`
- **Method:** `PUT`
- **Description:** Updates the stock quantity of a product
- **Request Body:**
  ```json
  {
    "stock": 150
  }
  ```
- **Response:** Returns the updated product object

#### Update Product Name
- **URL:** `/branches/{branchId}/products/{productId}/name`
- **Method:** `PUT`
- **Description:** Updates the name of a product
- **Request Body:**
  ```json
  {
    "name": "New Product Name"
  }
  ```
- **Response:** Returns the updated product object

#### Get Products with Maximum Stock by Franchise
- **URL:** `/franchises/{franchiseId}/products/max-stock`
- **Method:** `GET`
- **Description:** Returns the product with the highest stock level for each branch in a franchise
- **Response:** Returns an array of products

## Project Architecture

This project follows a clean architecture approach with these layers:

1. **Domain Layer**
   - Entities: Core business objects
   - Interfaces: Repository and use case interfaces

2. **Application Layer**
   - Services: Implementation of use cases

3. **Infrastructure Layer**
   - Controllers: REST endpoints
   - Repository Adapters: MongoDB implementation of repositories
   - Entities: Database models
   - Mappers: Convert between domain and infrastructure entities

4. **Configuration**
   - MongoDB configuration
   - OpenAPI (Swagger) configuration