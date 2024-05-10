USE moviedb;
DELIMITER //

CREATE PROCEDURE add_star(IN starName VARCHAR(255), IN birthYear YEAR)
BEGIN
    DECLARE newId INT;
    DECLARE newIdString CHAR(9);

    -- Get the maximum id number used, strip the prefix, increment it
SELECT IFNULL(MAX(CAST(SUBSTRING(id, 3) AS UNSIGNED)), 0) + 1 INTO newId FROM stars WHERE id LIKE 'nm%';

-- Prepare the new ID as a string with the 'nm' prefix
SET newIdString = CONCAT('nm', LPAD(newId, 7, '0'));

    -- Insert the new star with the generated ID
INSERT INTO stars (id, name, birthYear) VALUES (newIdString, starName, birthYear);
END //

DELIMITER ;
