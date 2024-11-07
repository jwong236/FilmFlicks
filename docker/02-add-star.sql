USE moviedb;
DROP PROCEDURE IF EXISTS add_star;
DELIMITER $$

CREATE PROCEDURE add_star(
    IN star_name VARCHAR(255),
    IN birth_year YEAR,
    OUT result_message VARCHAR(255)
)
BEGIN
    DECLARE new_star_id CHAR(9);

    -- Generate a new unique ID for the star
    SELECT CONCAT('nm', LPAD(IFNULL(MAX(CAST(SUBSTRING(id, 3) AS UNSIGNED)), 0) + 1, 7, '0')) INTO new_star_id
    FROM stars;

    -- Insert the new star
    INSERT INTO stars (id, name, birth_year) VALUES (new_star_id, star_name, birth_year);

    -- Set the OUT parameter with the success message
    SET result_message = CONCAT('New star added with ID: ', new_star_id);
END$$

DELIMITER ;
