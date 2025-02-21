# CO2 Emission Calculator

A Java command-line tool that calculates the amount of CO₂-equivalent (in kilograms) produced when traveling between two cities via a specified transportation method.

## Overview

This tool queries the [OpenRouteService Geocode & Matrix APIs](https://openrouteservice.org/dev/#/api-docs) to:
1. **Geocode** city names into coordinates.
2. **Compute** distances between cities (in kilometers).
3. **Calculate** CO₂-equivalent using predefined emission factors (in `Emission.java`) for various vehicle types.

It outputs the total emissions in **kilograms** of CO₂-equivalent.

---

## Features

- **Command-line interface** accepts parameters in any order:
    - `--start`: The starting city.
    - `--end`: The destination city.
    - `--transportation-method`: The type of vehicle.
- **Emission data** stored in an `enum` for easy maintenance.
- **Externalized configuration** in `application.properties` and environment variable `ORS_TOKEN`.
- **Unit tested** with JUnit 5 and Mockito.
- **API fallback** if environment variable is missing, warns and uses `application.properties`.

---

## Requirements

1. **Java** (17 or newer).
2. **Maven** (3.6+ recommended) for building and running tests.
3. **OpenRouteService Token** set as `ORS_TOKEN` environment variable (or in `application.properties` for fallback).

---

## Compile and Run

mvn clean install
java -cp target/co2-emission-1.0-SNAPSHOT.jar com.sap.co2.App --start=Berlin --end=Hamburg --transportation-method=diesel-car-medium

or

mvn clean compile
mvn exec:java -Dexec.mainClass="com.sap.co2.App" -Dexec.args="--start Berlin --end Hamburg --transportation-method diesel-car-medium"

Command-Line Arguments
--start or --start=
--end or --end=
--transportation-method or --transportation-method=

## Unit Tests

mvn clean test
