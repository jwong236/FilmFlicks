package com.filmflicks.repositories;

import com.filmflicks.models.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<Star, String> {

    // Call stored procedure for adding a star
    @Procedure(name = "add_star")
    String addStar(@Param("name") String name, @Param("birth_year") int birthYear);

    // Find all distinct star names
    @Query("SELECT DISTINCT s.name FROM Star s")
    List<String> findAllStarNames();

    // Find star by exact name (case-insensitive)
    Optional<Star> findByNameIgnoreCase(String name);
}