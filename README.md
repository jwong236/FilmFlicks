# FilmFlix

## Overview
FilmFlix is a movie information fullstack web application that allows users to browse detailed information about movies, search by various criteria, and interact through a responsive, user-friendly interface. Originally developed with a backend using JDBC and servlets, this project is being refactored into a Spring Boot application leveraging JPA standards for improved maintainability, scalability, and code quality.

## Key Features
- **Movie Browsing and Search**: Users can browse a catalog of movies, search based on title, genre, and actor, and view detailed pages with in-depth information for each movie.
- **User-Friendly and Responsive UI**: The frontend is developed with React, styled for ease of navigation, and optimized for various devices.
- **Secure User Authentication**: Login and authentication process with Spring Security, encryption with BCrypt, session management.
- **Scalable and Optimized Backend**: Ongoing improvements to transition endpoints to Spring Boot with JPA

## Demo
- Access the deployed version here: http://3.101.38.150/
  - email: b2@email.com
  - password: b2
- Screenshots and demos to be added soon

## Installation and Setup
To set up the project (Not recommended):

1. **Clone the repository**:
   ```bash
   git clone https://github.com/jwong236/FilmFlicks
   cd FilmFlicks
   ```
2. **Build the Docker images**:
   ```bash
   cd docker
   docker-compose build
   docker-compose up -d
   ```
3. **Access the Application**:
   ```bash
   http://localhost:3000
   ```

## Technologies Used
- **Frontend**: React, Vite
- **Backend**: Spring Boot, JPA (previously Tomcat, JDBC and servlets)
- **Database**: MySQL
- **Deployment**: AWS EC2
- **Additional Tools**: Docker for containerization, NGINX for reverse proxying, JMeter for stress testing, Kubernetes for scalability (removed on this version)

## Database Schema
![moviedb-db](https://github.com/user-attachments/assets/6fe36319-70c4-44a6-b022-b6421b8088bf)

## Transition to Spring Boot and JPA
This project initially used Tomcat, JDBC and servlets for backend functionality, but refactoring to Spring Boot and JPA has introduced a more modular and efficient architecture. The following enhancements are being incorporated:

- **Spring Boot for RESTful Endpoints**: Each endpoint is restructured in Spring Boot, following standard practices and using controller classes and services to handle requests and manage data flow.
- **JPA and Repository Pattern**: Transitioning from direct SQL statements to the JPA repository pattern, allowing for better management of database operations and a cleaner codebase.
- **Improved Database Interaction**: JPA’s ORM capabilities simplify CRUD operations and allow for more effective data management through entity mappings, reducing boilerplate code.

## Future Plans
- This project is essentially complete and has served its purpose as practice. I will likely not revisit this project but if I do, here are some improvements:
- **Expand Database**: Kaggle has multiple available sources of movie data from various platforms such as Hulu, Netflix, Amazon videos etc. Data would need to be normalized and routed into the database
- **Recaptcha**: Improve security with ReCaptcha
- **Implement Caching Strategies**: Explore Redis caching for improved response times, particularly for frequently accessed endpoints.
- **Enhance Search Functionality**: Integrate fuzzy search and full-text search improvements using JPA features and indexing strategies for faster search queries.
- **Improve load balancing with Kubernetes, monitor with JMeter**: This project initially utilized Kubernetes and JMeter for load balancing and stress testing but now that the project is at a deployable state it is not a priority or a necessity

## Contributions and Acknowledgments
Initial contributions by Andy Phu and Jacob Wong included end-to-end development of the backend, frontend, database, and deployment configurations, completing the project with initial framework specifications (servlets and basic JDBC). The dataset and additional guidance were provided by Professor Chen Li and TAs Yicong Huang and Xinyuan Lin.
