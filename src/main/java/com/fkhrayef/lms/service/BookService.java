package com.fkhrayef.lms.service;

import com.fkhrayef.lms.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    List<Book> books = new ArrayList<>( Arrays.asList(
            new Book(101, "Calculus", "Mathew"),
            new Book(102, "Arabic", "Abdulaziz")
    ));

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(int bookId) {
        return books.stream()
                .filter(p -> p.getId() == bookId)
                .findFirst();
    }

    public Book addBook(Book book) {
        books.add(book);
        return book;
    }

    public boolean updateBook(Book book) {
        int index = -1; // Start with an invalid index
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                index = i; // Store the index where the book is found
                break; // Exit loop as soon as the book is found
            }
        }
        if (index != -1) {
            books.set(index, book); // Update if the book was found
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteBook(int bookId) {
        int index = -1; // Start with an invalid index
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == bookId) {
                index = i; // Store the index where the book is found
                break; // Exit loop as soon as the book is found
            }
        }

        if (index != -1) {
            books.remove(index); // Delete if the book was found
            return true;
        } else {
            return false;
        }
    }
}
