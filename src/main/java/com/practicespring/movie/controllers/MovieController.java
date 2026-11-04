package com.practicespring.movie.controllers;

import com.practicespring.movie.models.Movie;
import com.practicespring.movie.repositories.MovieRepository;
import com.practicespring.movie.services.GeminiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GeminiService geminiService;

    @GetMapping
    public List<Movie> displayAllMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Movie> addNewMovie(@RequestBody @Valid Movie newMovie) {
        String aiDescription = geminiService.generateMovieDescription(newMovie.getTitle());
        newMovie.setDescription(aiDescription);
        Movie savedMovie = movieRepository.save(newMovie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return ResponseEntity.ok().body("Movie deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
