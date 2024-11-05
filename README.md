# FilmFlix

## Overview
FilmFlix is a movie information fullstack web application that allows users to browse detailed information about movies, search by various criteria, and interact through a responsive, user-friendly interface. Originally developed with a backend using JDBC and servlets, this project is being refactored into a Spring Boot application leveraging JPA standards for improved maintainability, scalability, and code quality.

## Key Features
- **Movie Browsing and Search**: Users can browse a catalog of movies, search based on title, genre, and actor, and view detailed pages with in-depth information for each movie.
- **User-Friendly and Responsive UI**: The frontend is developed with React, styled for ease of navigation, and optimized for various devices.
- **Secure User Authentication**: Login and authentication process with Spring Security, encryption with BCrypt, reCAPTCHA integration and session management.
- **Scalable and Optimized Backend**: Ongoing improvements to transition endpoints to Spring Boot with JPA

## Demo
- Screenshots and demos to be added soon

## Installation and Setup
To set up the project:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/jwong236/FilmFlicks
   ```
2. **Navigate to the backend and frontend directories to install dependencies and set up your environment**:
- **Backend (Spring Boot)**: Run `mvn clean install` in the backend directory to install dependencies.
- **Frontend (React)**: Run `npm install` in the frontend directory.
3. **Start the application**:
- **Backend**: Use `mvn spring-boot:run` to start the Spring Boot server.
- **Frontend**: Use `npm start` to start the React frontend.

## Technologies Used
- **Frontend**: React, Vite
- **Backend**: Spring Boot, JPA (previously Tomcat, JDBC and servlets)
- **Database**: MySQL
- **Deployment**: AWS
- **Additional Tools**: Docker for containerization, JMeter for stress testing, Kubernetes for scalability

## Database Schema
![moviedb-db](https://github.com/user-attachments/assets/6fe36319-70c4-44a6-b022-b6421b8088bf)



## Transition to Spring Boot and JPA
This project initially used Tomcat, JDBC and servlets for backend functionality, but refactoring to Spring Boot and JPA has introduced a more modular and efficient architecture. The following enhancements are being incorporated:

- **Spring Boot for RESTful Endpoints**: Each endpoint is restructured in Spring Boot, following standard practices and using controller classes and services to handle requests and manage data flow.
- **JPA and Repository Pattern**: Transitioning from direct SQL statements to the JPA repository pattern, allowing for better management of database operations and a cleaner codebase.
- **Improved Database Interaction**: JPA’s ORM capabilities simplify CRUD operations and allow for more effective data management through entity mappings, reducing boilerplate code.

## Future Plans
- **Complete Transition to Spring Boot**: Convert all endpoints to Spring Boot to unify the backend structure and improve code readability and testing.
- **Redeploy and Optimize**: After transitioning, redeploy the application and evaluate performance using JMeter and other profiling tools.
- **Implement Caching Strategies**: Explore Redis caching for improved response times, particularly for frequently accessed endpoints.
- **Enhance Search Functionality**: Integrate fuzzy search and full-text search improvements using JPA features and indexing strategies for faster search queries.

## Contributions and Acknowledgments
Initial contributions by Andy Phu and Jacob Wong included end-to-end development of the backend, frontend, database, and deployment configurations, completing the project with initial framework specifications (servlets and basic JDBC). The dataset and additional guidance were provided by Professor Chen Li and TAs Yicong Huang and Xinyuan Lin.
