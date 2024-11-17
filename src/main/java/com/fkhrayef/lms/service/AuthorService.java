package com.fkhrayef.lms.service;

import com.fkhrayef.lms.dto.AuthorDto;
import com.fkhrayef.lms.dto.BookDto;
import com.fkhrayef.lms.model.Author;
import com.fkhrayef.lms.repo.AuthorRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepo repo;

    public AuthorService(AuthorRepo repo) {
        this.repo = repo;
    }

    public List<AuthorDto> getAllAuthors() {
        return repo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Method to map Author to AuthorDto
    private AuthorDto mapToDto(Author author) {
        List<BookDto> bookDtos = author.getBooks().stream()
                .map(book -> new BookDto(book.getId(), book.getTitle(), author.getName()))
                .collect(Collectors.toList());

        return new AuthorDto(author.getId(), author.getName(), bookDtos);
    }

    public Optional<Author> getAuthorById(Long authorId) {
        return repo.findById(authorId);
    }

    public Author addAuthor(Author author) {
        repo.save(author);
        return author;
    }

    public Author updateAuthor(Author author) {
        repo.save(author);
        return author;
    }

    public void deleteAuthor(Long authorId) {
        repo.deleteById(authorId);
    }
}