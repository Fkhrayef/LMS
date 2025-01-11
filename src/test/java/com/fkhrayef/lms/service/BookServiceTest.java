package com.fkhrayef.lms.service;

import com.fkhrayef.lms.dto.BookDto;
import com.fkhrayef.lms.dto.BookResponse;
import com.fkhrayef.lms.model.Author;
import com.fkhrayef.lms.model.Book;
import com.fkhrayef.lms.repo.AuthorRepo;
import com.fkhrayef.lms.repo.BookRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private BookService bookService;

    @Test
    public void BookService_AddBook_ReturnsBookDto() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        author = authorRepo.save(author);

        Book book = Book.builder()
                .title("Computer Networks")
                .author(author).build();

        BookDto bookDto = BookDto.builder()
                .title("Computer Networks")
                .authorName("Andrew S. Tanenbaum").build();

        when(bookRepo.save(Mockito.any(Book.class))).thenReturn(book);

        // Act
        BookDto savedBook = bookService.addBook(bookDto);

        // Assert
        Assertions.assertThat(savedBook).isNotNull();
    }

    @Test
    public void BookService_GetAllBooks_ReturnsResponseDto() {
        // Arrange
        Page<Book> books = Mockito.mock(Page.class);

        when(bookRepo.findAll(Mockito.any(Pageable.class))).thenReturn(books);

        // Act
        BookResponse saveBook = bookService.getAllBooks(1, 10);

        // Assert
        Assertions.assertThat(saveBook).isNotNull();
    }

    @Test
    public void BookService_GetBookById_ReturnsBookDto() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        author = authorRepo.save(author);

        Book book = Book.builder()
                .title("Computer Networks")
                .author(author).build();

        when(bookRepo.findById(1L)).thenReturn(Optional.ofNullable(book));

        // Act
        Optional<BookDto> savedBook = bookService.getBookById(1L);

        // Assert
        Assertions.assertThat(savedBook).isNotNull();
    }

    @Test
    public void BookService_UpdateBook_ReturnsBookDto() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        author = authorRepo.save(author);

        Book book = Book.builder()
                .title("Computer Networks")
                .author(author).build();

        BookDto bookDto = BookDto.builder()
                .id(1L)
                .title("Computer Networks")
                .authorName("Andrew S. Tanenbaum").build();

        when(bookRepo.findById(1L)).thenReturn(Optional.ofNullable(book));
        when(bookRepo.save(Mockito.any(Book.class))).thenReturn(book);

        // Act
        BookDto savedBook = bookService.updateBook(bookDto);

        // Assert
        Assertions.assertThat(savedBook).isNotNull();
    }

    @Test
    public void BookService_DeleteBookById_ReturnsBookDto() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        author = authorRepo.save(author);

        Book book = Book.builder()
                .title("Computer Networks")
                .author(author).build();

        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookRepo).deleteById(1L);

        // Act
        bookService.deleteBook(1L);

        // Assert
        verify(bookRepo, times(1)).deleteById(1L);
        when(bookRepo.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(bookRepo.findById(1L)).isEmpty();
    }

    @Test
    public void BookService_FindBooksByTitleOrAuthor_ReturnsBookDto() {
        // Arrange
        List<Book> books = List.of(
                Book.builder().title("Computer Networks").author(Author.builder().name("Andrew S. Tanenbaum").build()).build(),
                Book.builder().title("Operating Systems").author(Author.builder().name("Andrew S. Tanenbaum").build()).build()
        );
        String title = "Computer Networks";
        String authorName = "Andrew S. Tanenbaum";

        when(bookRepo.findByTitleOrAuthor(title, authorName)).thenReturn(books);

        // Act
        List<BookDto> bookDtos = bookService.findBooksByTitleOrAuthor(title, authorName);

        // Assert
        Assertions.assertThat(bookDtos).isNotNull();
        Assertions.assertThat(bookDtos.size()).isEqualTo(2);
        Assertions.assertThat(bookDtos.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(bookDtos.get(0).getAuthorName()).isEqualTo(authorName);
        Assertions.assertThat(bookDtos.get(1).getTitle()).isEqualTo("Operating Systems");
        Assertions.assertThat(bookDtos.get(1).getAuthorName()).isEqualTo(authorName);
    }
}
