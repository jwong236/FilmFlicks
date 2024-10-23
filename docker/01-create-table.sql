CREATE DATABASE IF NOT EXISTS moviedb;
USE moviedb;

CREATE TABLE movies (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    year INTEGER NOT NULL,
    director VARCHAR(100) NOT NULL,
    FULLTEXT (title)
);

CREATE TABLE stars (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  birth_year INTEGER DEFAULT NULL
);

CREATE TABLE stars_in_movies (
  star_id VARCHAR(36) NOT NULL,
  movie_id VARCHAR(36) NOT NULL,
  FOREIGN KEY (star_id) REFERENCES stars(id),
  FOREIGN KEY (movie_id) REFERENCES movies(id),
  PRIMARY KEY (star_id, movie_id)
);

CREATE TABLE genres (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(32) NOT NULL
);

CREATE TABLE genres_in_movies (
  genre_id INTEGER NOT NULL,
  movie_id VARCHAR(36) NOT NULL,
  FOREIGN KEY (genre_id) REFERENCES genres(id),
  FOREIGN KEY (movie_id) REFERENCES movies(id),
  PRIMARY KEY (genre_id, movie_id)
);

CREATE TABLE credit_cards (
  id VARCHAR(20) NOT NULL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  expiration DATE NOT NULL
);

CREATE TABLE customers (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  cc_id VARCHAR(20) NOT NULL,
  address VARCHAR(200) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  FOREIGN KEY (cc_id) REFERENCES credit_cards(id)
);

CREATE TABLE sales (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  customer_id INTEGER NOT NULL,
  movie_id VARCHAR(36) NOT NULL,
  sale_date DATE NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customers(id),
  FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE ratings (
  movie_id VARCHAR(36) NOT NULL,
  rating FLOAT NOT NULL,
  num_votes INTEGER NOT NULL,
  FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE movie_prices (
    movie_id VARCHAR(36) NOT NULL PRIMARY KEY,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE employees (
    email VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100)
);
