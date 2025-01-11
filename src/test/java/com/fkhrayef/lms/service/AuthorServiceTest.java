package com.fkhrayef.lms.service;

import com.fkhrayef.lms.dto.AuthorDto;
import com.fkhrayef.lms.dto.BookDto;
import com.fkhrayef.lms.model.Author;
import com.fkhrayef.lms.model.Book;
import com.fkhrayef.lms.repo.AuthorRepo;
import com.fkhrayef.lms.repo.BookRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepo authorRepo;
    @Mock
    private BookRepo bookRepo;
    @InjectMocks
    private AuthorService authorService;

    private Author author;
    private Book book;
    private AuthorDto authorDto;
    private BookDto bookDto;

    @BeforeEach
    public void init() {
        author = Author.builder().name("Andrew S. Tanenbaum").build();
        authorDto = AuthorDto.builder().name("Andrew S. Tanenbaum").build();
        book = Book.builder().title("Computer Networks").author(author).build();
        bookDto = BookDto.builder().title("Computer Networks").authorName("Andrew S. Tanenbaum").build();
    }

    @Test
    public void AuthorService_GetAllAuthors_ReturnsAuthorDto() {
        // Arrange
        List<Book> books = List.of(
                book,
                book
        );
        List<Author> authors = List.of(
                Author.builder().name("Mark S. Henry").books(books).build(),
                Author.builder().name("Douglas Comer").books(books).build()
        );
        Mockito.when(authorRepo.findAll()).thenReturn(authors);

        // Act

        List<AuthorDto> authorDtos = authorService.getAllAuthors();

        // Assert
        Assertions.assertThat(authorDtos).isNotNull();
        Assertions.assertThat(authorDtos.size()).isEqualTo(2);
        Assertions.assertThat(authorDtos.get(0).getName()).isEqualTo("Mark S. Henry");
        Assertions.assertThat(authorDtos.get(1).getName()).isEqualTo("Douglas Comer");
    }

    @Test
    public void AuthorService_AddAuthor_ReturnAuthorDto() {
        // Arrange
        when(authorRepo.save(Mockito.any(Author.class))).thenReturn(author);

        // Act
        Author savedAuthor =  authorService.addAuthor(author);

        // Assert
        Assertions.assertThat(savedAuthor).isNotNull();
    }

    @Test
    public void AuthorService_GetAuthorById_ReturnsAuthor() {
        // Arrange
        when(authorRepo.findById(1L)).thenReturn(Optional.ofNullable(author));

        // Act
        Optional<Author> savedAuthor = authorService.getAuthorById(1L);

        // Assert
        Assertions.assertThat(savedAuthor).isNotNull();
    }

    @Test
    public void AuthorService_UpdateAuthor_ReturnsAuthor() {
        // Arrange
        when(authorRepo.save(Mockito.any(Author.class))).thenReturn(author);

        // Act
        Author savedAuthor =  authorService.updateAuthor(author);

        // Assert
        Assertions.assertThat(savedAuthor).isNotNull();
    }

    @Test
    public void AuthorService_DeleteAuthor_ReturnsAuthor() {
        // Arrange
        Long authorId = 1L;

        // Act
        assertDoesNotThrow(() -> authorService.deleteAuthor(authorId));

        // Assert
        Mockito.verify(authorRepo).deleteById(authorId);
    }
}
