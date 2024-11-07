USE moviedb;
DROP PROCEDURE IF EXISTS delete_star;
DELIMITER $$

CREATE PROCEDURE delete_star(
    IN star_id CHAR(9),
    OUT result_message VARCHAR(255)
)
BEGIN
    -- Check if the star exists
    IF EXISTS (SELECT 1 FROM stars WHERE id = star_id) THEN
        -- Delete the star
        DELETE FROM stars WHERE id = star_id;
        
        -- Set the success message in the OUT parameter
        SET result_message = CONCAT('Star with ID ', star_id, ' has been successfully deleted.');
    ELSE
        -- Set the error message in the OUT parameter
        SET result_message = CONCAT('Error: Star with ID ', star_id, ' does not exist.');
    END IF;
END$$

DELIMITER ;
