package com.fkhrayef.lms.controller;

import com.fkhrayef.lms.dto.AuthorDto;
import com.fkhrayef.lms.model.Author;
import com.fkhrayef.lms.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAuthors() {
        return new ResponseEntity<>(service.getAllAuthors(), HttpStatus.OK);
    }

    @GetMapping("/authors/{authorId}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long authorId) {
        return service.getAuthorById(authorId)
                .map(author -> ResponseEntity.ok(author)) // If found, return 200 OK with the book
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build()); // If not found, return 404 Not Found
    }
    @PostMapping("/authors")
    public ResponseEntity<Author> addAuthor(@RequestBody @Valid Author author) {
        Author author1 = service.addAuthor(author);
        return new ResponseEntity<>(author1, HttpStatus.CREATED);
    }

    @PutMapping("/authors")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        if (service.getAuthorById(author.getId()).isPresent()) {
            Author author1 = service.updateAuthor(author);
            return new ResponseEntity<>(author1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/authors/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
        if (service.getAuthorById(authorId).isPresent()) {
            service.deleteAuthor(authorId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}