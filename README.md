# Movie Information WebApp

## Description
This web application provides detailed movie information to users. It includes features such as listing movies, viewing detailed pages for each movie, and user interactions. The webapp is designed to be responsive and user-friendly, utilizing a modern tech stack for both the frontend and backend.


## Demo Project 1
[Click here to view the demo](https://youtu.be/klhI5wOVID8)
## Demo Project 2
[Click here to view the demo](https://youtu.be/HGAe0ROsfOs)
## Demo Project 3
[Click here to view the demo]([https://youtu.be/HGAe0ROsfOs](https://youtu.be/De-1VMAlrHc))
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

### Project 3
#### Andy Phu
- **reCAPTCHA for Login:** Implemented reCAPTCHA to enhance the security during the user login
- **HTTPS Configuration:** Configured HTTPS to ensure secure data transmission and improve the overall security of the web application.
- **Prepared Statements:** Upgraded all SQL statements to prepared statements to prevent SQL injection and enhance database security.
- **XML Parsing:** Implemented XML parsing to use new sources of data to increase the content size of the movie database.
- **Domain Registration:** Registered a domain name for the project, establishing a professional web presence.

#### Jacob Wong
- **Password Encryption:** Added password encryption to the login process to enhance user data security.
- **Employee Dashboard Implementation:** Developed an employee dashboard to insert movies and stars to the database
- **Employee Login with reCAPTCHA:** Implemented an employee login feature integrated with reCAPTCHA

#### All Prepared Statements files
- Add.java, BrowseCharacter.java, BrowseGenre.java, CastInsert.java, Delete.java, DomParser.java, EmployeeLogin.java, EncryptPasswords.java, Login.java, Metadata.java, MovieInsert.java, Payment.java, PreviousGetter.java, Search.java, ShoppingCart.java, SingleMovie.java, SingleStar.java, StarInsert.java, Subtract.java, TopMovies.java, TotalPrice.java

#### XML Parser
Cast: 25851
Stars: 3029
Movies: 4778
Unoptimized Run Time: 40 seconds
Optimized Run Time: 20 Seconds
Things I did to optimize:
1) Batch Insert
2) Hash Map for genre translations and checking for duplicates
