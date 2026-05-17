# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Boot + MyBatis management system with standard layered architecture (Controller → Service → Mapper).

## Build & Run

```bash
mvn clean package -DskipTests           # Build JAR
java -jar target/tlias-web-manange-*.jar  # Run
mvn spring-boot:run                      # Or run via Maven
mvn test                                 # Run tests
```

## Architecture

- **Controller**: REST endpoints, returns `Result` object (code/msg/data)
- **Service**: Business logic, interface + impl pattern
- **Mapper**: MyBatis data access, annotation-based for simple CRUD, XML for complex queries

Key packages: `org.example.controller`, `org.example.service`, `org.example.mapper`, `org.example.pojo`

## Data Access Patterns

- **Simple CRUD**: Use MyBatis annotations directly on `*Mapper` interfaces (see `DeptMapper`)
- **Complex queries**: Use XML mappers in `src/main/resources/org/example/mapper/` (see `EmpMapper.xml`)
- XML mapper namespace must match interface fully qualified name (e.g., `org.example.mapper.EmpMapper`)
- `map-underscore-to-camel-case: true` enabled — DB snake_case maps to Java camelCase

## Key Conventions

- `Result.success(Object)` returns data with code=1
- `Result.error(String)` returns error with code=0
- Delete operations use `@RequestParam` with `required=false` for optional params
- Date format: `yyyy-MM-dd` via Jackson config in `application.yml`

## Dependencies

Spring Boot Web, MyBatis (4.0.1), MySQL Connector, PageHelper (1.4.7), Lombok, Jackson