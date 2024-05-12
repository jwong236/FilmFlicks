CREATE DATABASE IF NOT EXISTS moviedb;
USE moviedb;

CREATE TABLE movies (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  year INTEGER NOT NULL,
  director VARCHAR(100) NOT NULL
);

CREATE TABLE stars (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  birthYear INTEGER DEFAULT NULL
);

CREATE TABLE stars_in_movies (
  starId VARCHAR(36) NOT NULL,
  movieId VARCHAR(36) NOT NULL,
  FOREIGN KEY (starId) REFERENCES stars(id),
  FOREIGN KEY (movieId) REFERENCES movies(id),
  PRIMARY KEY (starId, movieId)
);

CREATE TABLE genres (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(32) NOT NULL
);

CREATE TABLE genres_in_movies (
  genreId INTEGER NOT NULL,
  movieId VARCHAR(36) NOT NULL,
  FOREIGN KEY (genreId) REFERENCES genres(id),
  FOREIGN KEY (movieId) REFERENCES movies(id),
  PRIMARY KEY (genreId, movieId)
);

CREATE TABLE creditcards (
  id VARCHAR(20) NOT NULL PRIMARY KEY,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  expiration DATE NOT NULL
);

CREATE TABLE customers (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  ccId VARCHAR(20) NOT NULL,
  address VARCHAR(200) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(20) NOT NULL,
  FOREIGN KEY (ccId) REFERENCES creditcards(id)
);

CREATE TABLE sales (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  customerId INTEGER NOT NULL,
  movieId VARCHAR(10) NOT NULL,
  saleDate DATE NOT NULL,
  FOREIGN KEY (customerId) REFERENCES customers(id),
  FOREIGN KEY (movieId) REFERENCES movies(id)
);

CREATE TABLE ratings (
  movieId VARCHAR(10) NOT NULL,
  rating FLOAT NOT NULL,
  numVotes INTEGER NOT NULL,
  FOREIGN KEY (movieId) REFERENCES movies(id)
);


CREATE TABLE movie_prices (
    movieId VARCHAR(10) NOT NULL PRIMARY KEY,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (movieId) REFERENCES movies(id)
);

CREATE TABLE employees (
                           email VARCHAR(50) PRIMARY KEY,
                           password VARCHAR(20) NOT NULL,
                           fullname VARCHAR(100)
);
INSERT INTO employees (email, password, fullname) VALUES ('classta@email.edu', 'classta', 'TA CS122B');