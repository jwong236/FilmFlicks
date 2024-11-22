# FilmFlix

## Overview
FilmFlix is a movie information fullstack web application that allows users to browse detailed information about movies, search by various criteria, and interact through a responsive, user-friendly interface. Originally developed with a backend using JDBC and servlets, this project is being refactored into a Spring Boot application leveraging JPA standards for improved maintainability, scalability, and code quality.

## Key Features
- **Movie Browsing and Search**: Users can browse a catalog of movies, search based on title, genre, and actor (autocomplete available), and view detailed pages with in-depth information for each movie or star.
- **Simulate Purchasing**: Add to your shopping cart, enter card details (not your real one), and checkout. All movies are $10 for fun.
- **User expandable database**: Add to the database yourself if its missing a movie that you like using the admin panel.
- **User-Friendly and Responsive UI**: Styled to be easy on the eyes for a smooth user experience. Responsive to ensure compatibility across different device sizes.
- **Secure User Authentication**: Login and authentication process with Spring Security, encryption with BCrypt, session management.

## Demo
- Access the deployed version here: http://3.101.38.150/
  - email: b2@email.com
  - password: b2
<details>
<summary>Click to view screenshots and GIFs!</summary>
  
### Login Page
![Login Page](https://github.com/user-attachments/assets/4998646d-b854-44d8-bc45-6e8c77736e51)

### Home Page
![Home Page](https://github.com/user-attachments/assets/d7b1ac39-78c7-44d9-ac3e-bd56d292327d)

### Movie List Page
![Movie List Page](https://github.com/user-attachments/assets/58d7d29e-8d80-4dd8-89fa-45770b190890)

### Shopping Cart Page
![Shopping Cart Feature](https://github.com/user-attachments/assets/8d838996-1090-4460-947b-d21dbfbde1ca)

### Checkout Page
![Checkout Page](https://github.com/user-attachments/assets/f696109c-78fc-42e4-a6f9-e5a0cddc752c)

### Confirmation Panel
![Confirmation Panel](https://github.com/user-attachments/assets/2f93cfd3-397c-489e-adff-b0c86edb1ff2)

### Movie Card
![Movie Card](https://github.com/user-attachments/assets/004bfe88-66bb-42eb-95d5-e0f462764608)

### Star Card
![Star Card](https://github.com/user-attachments/assets/080f0a17-5ef5-4002-bc9a-17eba613e819)

### Autocomplete Search
![Autocomplete Search](https://github.com/user-attachments/assets/d192a6c1-24a9-478a-880a-6ec722f87609)

### Admin Page
![Admin Page](https://github.com/user-attachments/assets/84c0e413-85f7-4d92-b57c-74235f8b3cee)

### Top Rated Movies
![Top Rated Movies](https://github.com/user-attachments/assets/1a0ed6fa-7c6f-4861-9a96-d2644c44d2b3)


</details>


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
- This project is essentially complete and has served its purpose as practice. I will likely not revisit this project but if I do, here are some improvements
- **Expand Database**: Kaggle has multiple available sources of movie data from various platforms such as Hulu, Netflix, Amazon videos etc. Data would need to be normalized and routed into the database.
- **Recaptcha**: Improve security with ReCaptcha
- **Implement Caching Strategies**: Explore Redis caching for improved response times, particularly for frequently accessed endpoints.
- **Enhance Search Functionality**: Integrate fuzzy search and full-text search improvements using JPA features and indexing strategies for faster search queries.
- **Improve load balancing with Kubernetes, monitor with JMeter**: This project initially utilized Kubernetes and JMeter for load balancing and stress testing but now that the project is at a deployable state it is not a priority or a necessity
- **Bug fix**: Deployed version's admin page can't be accessed, issue with request URL

## Contributions and Acknowledgments
Initial contributions by Andy Phu and Jacob Wong included end-to-end development of the backend, frontend, database, and deployment configurations, completing the project with initial framework specifications (servlets and basic JDBC). The dataset and additional guidance were provided by Professor Chen Li and TAs Yicong Huang and Xinyuan Lin.
