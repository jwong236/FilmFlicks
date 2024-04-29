# Movie Information WebApp

## Description
This web application provides detailed movie information to users. It includes features such as listing movies, viewing detailed pages for each movie, and user interactions. The webapp is designed to be responsive and user-friendly, utilizing a modern tech stack for both the frontend and backend.

## Demo
[Click here to view the demo](https://youtu.be/klhI5wOVID8)

## Contributions

### Project 1
#### Andy Phu
- **Base Repository Setup:** Created the initial repositories required for the project.
- **Backend Development:** Developed backend endpoints in `backend.java`, focusing on the implementation of `MovieList.java` and `SingleStar.java`.
- **Frontend Implementation:** Implemented React components `MovieList` and `SingleStar`.
- **Styling:** Initiated the styling of the application using Tailwind CSS.
- **Deployment:** Deployed the application on an AWS instance, ensuring live public accessibility.

#### Jacob Wong
- **Single Movie Feature:** Developed `SingleMovie.java` and the corresponding React component `SingleMovie`, which handles displaying detailed information about individual movies.
- **Styling:** Took charge of the overall styling for the webpages, ensuring a cohesive visual experience.
- **Database Design:** Created the SQL database schema which structures the entire database used for storing movie data.


### Project 2
#### Andy Phu
- **Login Feature Implementation:** Implemented the login feature using endpoints to manage user authentication.
- **Login Filter Development:** Developed a login filter to ensure authentication checks across protected endpoints.
- **Shopping Cart Functionality:** Implemented the shopping cart functionality using session management to track user cart data.
- **Session-Based Movielist Navigation:** Developed endpoints to handle returning to the movielist page with session data intact, ensuring user selections and settings are preserved.

#### Jacob Wong
- **Frontend Webpages Creation:** Created the frontend webpages for Login, Homepage, MovieList, ShoppingCart, PaymentInfo, and Confirmation pages
- **Search and Browse Endpoints:** Wrote backend endpoints to support search and browse functionalities

#### LIKE Predicate
- **Usage:** In the servlet, the LIKE keyword is used with SQL queries to enable partial and case-insensitive searches for movie titles, directors, and stars. It allows users to find movies based on substrings, making the search feature flexible and user-friendly. Wildcards (%) are used with LIKE to match any sequence of characters before and after the search terms.