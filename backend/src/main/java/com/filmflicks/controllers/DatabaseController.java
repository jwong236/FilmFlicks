package com.filmflicks.controllers;

import com.filmflicks.models.Sale;
import com.filmflicks.repositories.MovieRepository;
import com.filmflicks.repositories.SaleRepository;
import com.filmflicks.repositories.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private SaleRepository saleRepository;

    @PostMapping("/movie/add-basic")
    public String addMovieBasic(@RequestParam String title, @RequestParam Integer year, @RequestParam String director) {
        return movieRepository.addMovieBasic(title, year, director);
    }

    @PostMapping("/movie/add-star")
    public String addMovieStars(@RequestParam String movieId, @RequestParam String starNames, @RequestParam String starBirthYears) {
        return movieRepository.addMovieStars(movieId, starNames, starBirthYears);
    }

    @PostMapping("/movie/add-genre")
    public String addMovieGenres(@RequestParam String movieId, @RequestParam String genreNames) {
        return movieRepository.addMovieGenres(movieId, genreNames);
    }

    @DeleteMapping("/movie/delete-basic")
    public String deleteMovieBasic(@RequestParam String movieId) {
        return movieRepository.deleteMovieBasic(movieId);
    }

    @DeleteMapping("/movie/delete-star")
    public String deleteMovieStars(@RequestParam String movieId) {
        return movieRepository.deleteMovieStars(movieId);
    }

    @DeleteMapping("/movie/delete-genre")
    public String deleteMovieGenres(@RequestParam String movieId) {
        return movieRepository.deleteMovieGenres(movieId);
    }

    @PostMapping("/star/add")
    public String addStar(@RequestParam String name, @RequestParam int birthYear) {
        return starRepository.addStar(name, birthYear);
    }

    @DeleteMapping("/star/delete")
    public String deleteStar(@RequestParam String starId) {
        return starRepository.deleteStar(starId);
    }

    // Does not use stored procedures

    @PostMapping("/sale/add")
    public ResponseEntity<Sale> addSale(@RequestParam int customerId, @RequestParam String movieId, @RequestParam Date saleDate) {
        Sale sale = new Sale();
        sale.setCustomerId(customerId);
        sale.setMovieId(movieId);
        sale.setSaleDate(saleDate);
        Sale savedSale = saleRepository.save(sale);
        return ResponseEntity.ok(savedSale);
    }

    @DeleteMapping("/sale/delete")
    public ResponseEntity<Map<String, Object>> deleteSale(@RequestParam int id) {
        Map<String, Object> response = new HashMap<>();
        if (saleRepository.existsById(id)) {
            saleRepository.deleteById(id);
            response.put("status", "success");
            response.put("message", "Sale with ID " + id + " has been deleted successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Sale with ID " + id + " not found.");
            return ResponseEntity.status(404).body(response);
        }
    }


}
