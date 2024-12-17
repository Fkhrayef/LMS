package com.fkhrayef.lms.service;

import com.fkhrayef.lms.dto.BookDto;
import com.fkhrayef.lms.dto.BookResponse;
import com.fkhrayef.lms.exceptions.BookNotFoundException;
import com.fkhrayef.lms.exceptions.DuplicateBookException;
import com.fkhrayef.lms.model.Author;
import com.fkhrayef.lms.model.Book;
import com.fkhrayef.lms.repo.AuthorRepo;
import com.fkhrayef.lms.repo.BookRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepo repo;
    private final AuthorRepo authorRepo;

    public BookService(BookRepo repo, AuthorRepo authorRepo) {
        this.repo = repo;
        this.authorRepo = authorRepo;
    }

    public BookResponse getAllBooks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Book> books = repo.findAll(pageable);
        List<Book> bookList = books.getContent();
        List<BookDto> content = bookList.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        BookResponse bookResponse = new BookResponse();
        bookResponse.setContent(content);
        bookResponse.setPageNo(books.getNumber());
        bookResponse.setPageSize(books.getSize());
        bookResponse.setTotalElements(books.getTotalElements());
        bookResponse.setTotalPages(books.getTotalPages());
        bookResponse.setLast(books.isLast());
        return bookResponse;
    }
    // Helper method for pagination in getAllBooks() endpoint.
    private BookDto mapToDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor() != null ? book.getAuthor().getName() : null
        );
    }

    @Cacheable(cacheNames = "books", key = "#bookId")
    public Optional<BookDto> getBookById(Long bookId) {
        System.out.println("Cache miss for book with ID: " + bookId);
        return repo.findById(bookId)
                .map(this::mapToDto);
    }

    public BookDto addBook(BookDto bookDto) {
        // Check for duplicate book by title
        boolean exists = repo.existsByTitle(bookDto.getTitle());
        if (exists) {
            throw new DuplicateBookException("Book with title '" + bookDto.getTitle() + "' already exists");
        }

        Author author = authorRepo.findByName(bookDto.getAuthorName())
                .orElse(null);
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(author);

        Book savedBook = repo.save(book);

        String authorName = savedBook.getAuthor() != null ? savedBook.getAuthor().getName() : null;

        return new BookDto(savedBook.getId(), savedBook.getTitle(), authorName);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "books", key = "#bookDto.id"),
            },
            put = {
                    @CachePut(cacheNames = "books", key = "#bookDto.id")
            }
    )
    public BookDto updateBook(BookDto bookDto) {
        Book book = repo.findById(bookDto.getId())
                .orElseThrow(() -> new BookNotFoundException("Book with id '" + bookDto.getId() + "' does not exist"));

        // Update fields
        book.setTitle(bookDto.getTitle());

        if (bookDto.getAuthorName() != null) {
            Author author = authorRepo.findByName(bookDto.getAuthorName()).orElse(null);
            book.setAuthor(author); // Keep it null-safe for now.
        }

        // Save and convert to DTO
        Book updatedBook = repo.save(book);
        String authorName = updatedBook.getAuthor() != null ? updatedBook.getAuthor().getName() : null; // potentially Author doesn't exist exception.
        return new BookDto(updatedBook.getId(), updatedBook.getTitle(), authorName);

    }



    @CacheEvict(cacheNames = "books", key = "#bookId")
    public void deleteBook(Long bookId) {
        repo.deleteById(bookId);
    }

    public List<BookDto> findBooksByTitleOrAuthor(String title, String authorName) {
        return repo.findByTitleOrAuthor(title, authorName).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}