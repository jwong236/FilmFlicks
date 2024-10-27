-- Drop the procedure if it already exists
USE moviedb;
DROP PROCEDURE IF EXISTS add_movie_basic;
DELIMITER $$

CREATE PROCEDURE add_movie_basic(
    IN movie_title VARCHAR(255),
    IN movie_year YEAR,
    IN movie_director VARCHAR(255),
    OUT output_message VARCHAR(255)
)
BEGIN
    DECLARE existing_movie_id CHAR(9);
    DECLARE new_movie_id CHAR(9);

    -- Check for existing movie
    SELECT id INTO existing_movie_id FROM movies
    WHERE title = movie_title AND year = movie_year AND director = movie_director;

    IF existing_movie_id IS NOT NULL THEN
        -- Set error message if the movie already exists
        SET output_message = 'Error: Movie already exists';
    ELSE
        -- Generate new movie ID
        SELECT CONCAT('tt', LPAD(IFNULL(MAX(CAST(SUBSTRING(id, 3) AS UNSIGNED)), 0) + 1, 7, '0'))
        INTO new_movie_id FROM movies;

        -- Insert the new movie
        INSERT INTO movies (id, title, year, director) VALUES (new_movie_id, movie_title, movie_year, movie_director);

        -- Set success message that includes the new movie ID
        SET output_message = CONCAT('Movie added successfully with ID: ', new_movie_id);
    END IF;
END$$

DELIMITER ;




-- Drop the procedure if it already exists
DROP PROCEDURE IF EXISTS add_movie_stars;
DELIMITER $$

CREATE PROCEDURE add_movie_stars(
    IN movie_id CHAR(9),
    IN star_names TEXT, -- Comma-separated star names
    IN star_birth_years TEXT, -- Comma-separated star birth years
    OUT output_message VARCHAR(255)
)
BEGIN
    DECLARE star_name VARCHAR(255);
    DECLARE star_birth_year YEAR;
    DECLARE star_id CHAR(9);
    DECLARE current_index INT DEFAULT 1;

    -- Loop through each star in the input
    WHILE current_index <= CHAR_LENGTH(star_names) - CHAR_LENGTH(REPLACE(star_names, ',', '')) + 1 DO
        SET star_name = TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(star_names, ',', current_index), ',', -1));
        SET star_birth_year = CAST(TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(star_birth_years, ',', current_index), ',', -1)) AS UNSIGNED);

        -- Check if the star exists
        SET star_id = NULL;
        SELECT id INTO star_id FROM stars
        WHERE name = star_name AND (birth_year = star_birth_year OR (birth_year IS NULL AND star_birth_year IS NULL));

        -- If the star does not exist, return an error message
        IF star_id IS NULL THEN
            SET output_message = CONCAT('Error: Star "', star_name, '" with birth year "', star_birth_year, '" does not exist');
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = output_message;
        END IF;

        -- Link the star with the movie
        INSERT INTO stars_in_movies (star_id, movie_id) VALUES (star_id, movie_id);

        SET current_index = current_index + 1;
    END WHILE;

    -- Set the output message
    SET output_message = 'Stars linked to movie successfully';
END$$

DELIMITER ;

-- Drop the procedure if it already exists
DROP PROCEDURE IF EXISTS add_movie_genres;
DELIMITER $$

CREATE PROCEDURE add_movie_genres(
    IN movie_id CHAR(9),
    IN genre_names TEXT, -- Comma-separated genre names
    OUT output_message VARCHAR(255)
)
BEGIN
    DECLARE genre_name VARCHAR(255);
    DECLARE genre_id INT;
    DECLARE current_index INT DEFAULT 1;

    -- Loop through each genre in the input
    WHILE current_index <= CHAR_LENGTH(genre_names) - CHAR_LENGTH(REPLACE(genre_names, ',', '')) + 1 DO
        SET genre_name = TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(genre_names, ',', current_index), ',', -1));

        -- Check if the genre exists
        SET genre_id = NULL;
        SELECT id INTO genre_id FROM genres WHERE name = genre_name;

        -- If the genre does not exist, return an error message
        IF genre_id IS NULL THEN
            SET output_message = CONCAT('Error: Genre "', genre_name, '" does not exist');
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = output_message;
        END IF;

        -- Link the genre with the movie
        INSERT INTO genres_in_movies (genre_id, movie_id) VALUES (genre_id, movie_id);

        SET current_index = current_index + 1;
    END WHILE;

    -- Set the output message
    SET output_message = 'Genres linked to movie successfully';
END$$

DELIMITER ;
