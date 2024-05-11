USE moviedb;
DELIMITER $$

CREATE PROCEDURE add_star(
    IN starName VARCHAR(255),
    IN starBirthYear YEAR
)
BEGIN
    DECLARE newStarId CHAR(9);

    -- Generate a new unique ID for the star
SELECT CONCAT('nm', LPAD(IFNULL(MAX(CAST(SUBSTRING(id, 3) AS UNSIGNED)), 0) + 1, 7, '0')) INTO newStarId
FROM stars;

-- Insert the new star
INSERT INTO stars (id, name, birthYear) VALUES (newStarId, starName, starBirthYear);

-- Output the new star ID
SELECT 'New star added ' AS message, newStarId AS starId;
END$$

DELIMITER ;
