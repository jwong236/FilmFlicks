USE moviedb;
DELIMITER $$

CREATE PROCEDURE add_movie(
    IN movieTitle VARCHAR(255),
    IN movieYear YEAR,
    IN movieDirector VARCHAR(255),
    IN starName VARCHAR(255),
    IN starBirthYear YEAR,
    IN genreName VARCHAR(255),
    OUT newMovieId CHAR(9),
    OUT newStarId CHAR(9),
    OUT newGenreId INT,
    OUT outputMessage VARCHAR(255)
)
    proc_label: BEGIN
    DECLARE existingMovieId CHAR(9);
    DECLARE existingStarId CHAR(9);
    DECLARE existingGenreId INT;
    DECLARE baseMessage VARCHAR(255) DEFAULT 'Movie added successfully';

    -- Check for existing movie
SELECT id INTO existingMovieId FROM movies
WHERE title = movieTitle AND year = movieYear AND director = movieDirector;

IF existingMovieId IS NOT NULL THEN
        SET outputMessage = 'Error: Movie already exists';
        LEAVE proc_label;
END IF;

    -- Generate new movie ID
SELECT CONCAT('tt', LPAD(IFNULL(MAX(CAST(SUBSTRING(id, 3) AS UNSIGNED)), 0) + 1, 7, '0')) INTO newMovieId FROM movies;

-- Insert new movie
INSERT INTO movies (id, title, year, director) VALUES (newMovieId, movieTitle, movieYear, movieDirector);

-- Check for existing star
SELECT id INTO existingStarId FROM stars
WHERE name = starName AND (birthYear = starBirthYear OR (birthYear IS NULL AND starBirthYear IS NULL));

IF existingStarId IS NULL THEN
        -- Generate new star ID
SELECT CONCAT('nm', LPAD(IFNULL(MAX(CAST(SUBSTRING(id, 3) AS UNSIGNED)), 0) + 1, 7, '0')) INTO newStarId FROM stars;
INSERT INTO stars (id, name, birthYear) VALUES (newStarId, starName, starBirthYear);
ELSE
        SET newStarId = existingStarId;
        SET baseMessage = CONCAT(baseMessage, ' with existing star');
END IF;

    -- Check for existing genre
SELECT id INTO existingGenreId FROM genres WHERE name = genreName;

IF existingGenreId IS NULL THEN
        -- Generate new genre ID
SELECT IFNULL(MAX(id), 0) + 1 INTO newGenreId FROM genres;
INSERT INTO genres (id, name) VALUES (newGenreId, genreName);
ELSE
        SET newGenreId = existingGenreId;
        SET baseMessage = IF(LOCATE('existing star', baseMessage) > 0, CONCAT(baseMessage, ' and existing genre'), CONCAT(baseMessage, ' with existing genre'));
END IF;

    -- Link movie with star and genre
INSERT INTO stars_in_movies (starId, movieId) VALUES (newStarId, newMovieId);
INSERT INTO genres_in_movies (genreId, movieId) VALUES (newGenreId, newMovieId);

-- Set the output message
SET outputMessage = baseMessage;
END $$

DELIMITER ;
