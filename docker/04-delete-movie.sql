USE moviedb;

-- Drop the procedure if it already exists
DROP PROCEDURE IF EXISTS delete_movie_basic;
DELIMITER $$

CREATE PROCEDURE delete_movie_basic(
    IN movie_id CHAR(9),
    OUT output_message VARCHAR(1000)
)
BEGIN
    DECLARE movie_exists INT;

    -- Declare exit handler for SQL exceptions
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
GET DIAGNOSTICS CONDITION 1 @sqlstate = RETURNED_SQLSTATE, @errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;
SET output_message = CONCAT('Error: Unable to delete movie. SQLSTATE: ', @sqlstate, ', Error Number: ', @errno, ', Message: ', @text);
END;

    -- Check if the movie exists
SELECT COUNT(*) INTO movie_exists FROM movies WHERE id = movie_id;

IF movie_exists = 0 THEN
        SET output_message = CONCAT('Error: Movie with ID "', movie_id, '" does not exist');
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = output_message;
END IF;

    -- Delete from ratings
DELETE FROM ratings WHERE movie_id = movie_id;

-- Delete from sales
DELETE FROM sales WHERE movie_id = movie_id;

-- Delete from movie_prices
DELETE FROM movie_prices WHERE movie_id = movie_id;

-- Delete the movie itself
DELETE FROM movies WHERE id = movie_id;

-- Set success message
SET output_message = CONCAT('Movie with ID "', movie_id, '" has been successfully deleted');
END$$

DELIMITER ;

-- Drop the procedure if it already exists
DROP PROCEDURE IF EXISTS delete_movie_stars;
DELIMITER $$

CREATE PROCEDURE delete_movie_stars(
    IN movie_id CHAR(9),
    OUT output_message VARCHAR(1000)
)
BEGIN
    DECLARE movie_exists INT;

    -- Check if the movie exists
SELECT COUNT(*) INTO movie_exists FROM movies WHERE id = movie_id;

IF movie_exists = 0 THEN
        SET output_message = CONCAT('Error: Movie with ID "', movie_id, '" does not exist');
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = output_message;
END IF;

    -- Delete from stars_in_movies
DELETE FROM stars_in_movies WHERE movie_id = movie_id;

-- Set success message
SET output_message = CONCAT('Stars linked to movie with ID "', movie_id, '" have been successfully removed');
END$$

DELIMITER ;

-- Drop the procedure if it already exists
DROP PROCEDURE IF EXISTS delete_movie_genres;
DELIMITER $$

CREATE PROCEDURE delete_movie_genres(
    IN movie_id CHAR(9),
    OUT output_message VARCHAR(1000)
)
BEGIN
    DECLARE movie_exists INT;

    -- Check if the movie exists
SELECT COUNT(*) INTO movie_exists FROM movies WHERE id = movie_id;

IF movie_exists = 0 THEN
        SET output_message = CONCAT('Error: Movie with ID "', movie_id, '" does not exist');
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = output_message;
END IF;

    -- Delete from genres_in_movies
DELETE FROM genres_in_movies WHERE movie_id = movie_id;

-- Set success message
SET output_message = CONCAT('Genres linked to movie with ID "', movie_id, '" have been successfully removed');
END$$

DELIMITER ;
