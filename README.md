Configure the application properties:
  spring.datasource.url=jdbc:mysql://localhost:3306/airbnbdatabase
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.datasource.username=root
  spring.datasource.password=password
  spring.jpa.show-sql=true
  spring.jpa.hibernate.ddl-auto=update


Airbnb Clone
This project is an Airbnb-like application that allows users to register, log in, and manage property listings. 
It provides authentication with JWT tokens and supports full CRUD operations for property management.

Features
User Authentication

Signup and login functionality with encrypted passwords
JWT token generation for authentication
Property Management

Create, retrieve, update, and delete properties
Retrieve properties by ID
List all properties or properties owned by a specific user

API Endpoints

Authentication
Signup
    POST /api/auth/signup
    Request Body:
    json
    {
      "username": "string",
      "password": "string",
      "email": "string",
      "fullName": "string",
      "phoneNumber": "string"
}

Login
    POST /api/auth/login
    Request Body:
    json
    {
      "username": "string",
      "password": "string"
    }

Property Management

Create Property

    POST /api/properties
    Request Body:
    json
    {
      "name": "string",
      "description": "string",
      "address": "string",
      "pricePerNight": "decimal",
      "numberOfBedrooms": "int",
      "numberOfBathrooms": "int",
      "isAvailable": "boolean",
      "drinkAllowed": "boolean",
      "petAllowed": "boolean",
      "maxCheckoutTimeInNights": "int",
      "extraCharges": "decimal"
    }

Retrieve All Properties
    GET /api/properties


Get Property by ID
    GET /api/properties/{id}
Update Property
    PUT /api/properties/{id}
    Request Body:
    json

    {
      "name": "string",
      "description": "string",
      "address": "string",
      "pricePerNight": "decimal",
      "numberOfBedrooms": "int",
      "numberOfBathrooms": "int",
      "isAvailable": "boolean",
      "drinkAllowed": "boolean",
      "petAllowed": "boolean",
      "maxCheckoutTimeInNights": "int",
      "extraCharges": "decimal"
    }
    
Delete Property
    DELETE /api/properties/{id}

Get User’s Properties
    GET /api/users/{userId}/properties
