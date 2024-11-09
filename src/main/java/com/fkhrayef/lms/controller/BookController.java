package com.fkhrayef.lms.controller;

import com.fkhrayef.lms.model.Book;
import com.fkhrayef.lms.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable int bookId) {
        return service.getBookById(bookId)
                .map(book -> ResponseEntity.ok(book)) // If found, return 200 OK with the book
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build()); // If not found, return 404 Not Found
    }

    @PostMapping("/books")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        Book book1 = service.addBook(book);
        return new ResponseEntity<>(book1, HttpStatus.CREATED);
    }

    @PutMapping("/books")
    public ResponseEntity<String> updateBook(@RequestBody Book book) {
        boolean updated = service.updateBook(book);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable int bookId) {
        boolean deleted = service.deleteBook(bookId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
