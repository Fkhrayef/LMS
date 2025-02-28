package com.fkhrayef.lms.controller;

import com.fkhrayef.lms.dto.BookDto;
import com.fkhrayef.lms.dto.BookResponse;
import com.fkhrayef.lms.exceptions.BookNotFoundException;
import com.fkhrayef.lms.service.BookService;
import jakarta.validation.Valid;
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
    public ResponseEntity<BookResponse> getBooks(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(service.getAllBooks(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long bookId) {
        return service.getBookById(bookId)
                .map(book -> ResponseEntity.ok(book)) // If found, return 200 OK with the book
                .orElseThrow(() -> new BookNotFoundException("Book could not be found"));
    }

    @PostMapping("/books")
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookDto bookDto) {
        BookDto book1 = service.addBook(bookDto);
        return new ResponseEntity<>(book1, HttpStatus.CREATED);
    }

    @PutMapping("/books")
    public ResponseEntity<BookDto> updateBook(@RequestBody @Valid BookDto bookDto) {
        BookDto book1 = service.updateBook(bookDto);
        return new ResponseEntity<>(book1, HttpStatus.OK);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        if (service.getBookById(bookId).isPresent()) {
            service.deleteBook(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books/search")
    public ResponseEntity<List<BookDto>> searchBooks(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) String authorName) {
        System.out.println("searching for:" + title + " " + authorName);
        List<BookDto> books = service.findBooksByTitleOrAuthor(title, authorName);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}