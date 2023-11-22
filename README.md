# E-Voting App

The E-Voting app is a secure and efficient platform for electronic voting. It provides a convenient way for voters to vote remotely and securely, and for parties or non-governmental organizations to publish their current researches/referendums, while ensuring the integrity of the voting process.

## Table of Contents
- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Usage](#usage)

## Introduction
The E-Voting App is a Java-based application that facilitates the electronic voting process. It enables voters to cast their votes securely.

## Technologies Used
- Java
- Spring Boot
- Spring Security
- PostgreSQL
- Angular
- Postman (For API testing and documentation)
- Layered Architecture

## Features
- Register Voter: Allows the registration of eligible voters, ensuring their inclusion in the electoral roll.
- Register Party/NGO: Enables the registration of political parties, non-governmental orgranizations allowing them to participate in the election.
- Cast Votes: Users can securely cast their votes through the application, ensuring their participation in the electoral process.
- Age Verification: Implements a validation mechanism to ensure that only eligible voters above 18 years old can register and cast their votes.
- View Result: Provides a feature to view real-time election results, allowing users to stay informed about the progress of the voting process.

## Usage
### Voter Registration API

- Endpoint: `/publishers/register`
- Method: POST

#### Request

```json
{
  "publicName":"Democratic Party",
  "founder":"Andrew Jackson",
  "user":{
    "username":"democratic_party",
    "password":"dppassword",
    "age":100,
    "userRole":"Publisher"
  }
}
```

#### Response

```json
{
  "publisherId": 1,
  "publicName": "Democratic Party",
  "founder": "Andrew Jackson",
  "user": {
    "username": "democratic_party",
    "password": "$2a$10$iDX/3OoUbiYnckt//iTcPOlxFGy2JzsITSChd6ZlS4lesvxdnDCsW",
    "age": 100,
    "userRole": "Publisher"
  }
}
```
### Voter Login API
- Endpoint: `/login`
- Method: POST

#### Request

```json
{
  "username": "commission_member",
  "password": "adminpassword"
}
```

#### Response

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjb21taXNzaW9uX21lbWJlciIsImV4cCI6MTcwMDY4NDY5NywiaXNzIjoiZVZvdGluZ0FwcCIsInJvbGVzIjpbIkNvbW1pc3Npb24gTWVtYmVyIl19.VyV5BHdwoTyjbTpCcfc5q66QqEdX8UAM1A3ZYNBPDNg",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjb21taXNzaW9uX21lbWJlciIsImV4cCI6MTcwMDY5MTI5NywiaXNzIjoiZVZvdGluZ0FwcCJ9.D7hqCZmwPCAOQ7y5AIcR64BD0VUNTrFRPAWX2bIv-eE"
}
```