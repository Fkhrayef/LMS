package com.fkhrayef.lms.repo;

import com.fkhrayef.lms.model.Author;
import com.fkhrayef.lms.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepoTests {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Test
    public void BookRepo_SaveAll_ReturnSavedBook() {

        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        author = authorRepo.save(author);

        Book book = Book.builder()
                .title("Computer Networks")
                .author(author).build();

        // Act
        Book savedBook = bookRepo.save(book);

        // Assert
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isGreaterThan(0);
        Assertions.assertThat(savedBook.getAuthor()).isEqualTo(author);
    }

    @Test
    public void BookRepo_GetAll_ReturnMoreThanOneBook() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        author = authorRepo.save(author);

        Book book1 = Book.builder()
                .title("Computer Networks")
                .author(author).build();

        Book book2 = Book.builder()
                .title("Computer Architecture")
                .author(author).build();

        bookRepo.save(book1);
        bookRepo.save(book2);

        // Act
        List<Book> bookList = bookRepo.findAll();

        // Assert
        Assertions.assertThat(bookList).isNotNull();
        Assertions.assertThat(bookList.size()).isEqualTo(2);
    }

    @Test
    public void BookRepo_FindById_ReturnBook() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        author = authorRepo.save(author);

        Book book = Book.builder()
                .title("Computer Networks")
                .author(author).build();

        bookRepo.save(book);

        // Act
        Book bookFound = bookRepo.findById(book.getId()).get();

        // Assert
        Assertions.assertThat(bookFound).isNotNull();
    }

    @Test
    public void BookRepo_ExistByTitle_ReturnTrue() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        author = authorRepo.save(author);

        Book book = Book.builder()
                .title("Computer Networks")
                .author(author).build();

        bookRepo.save(book);

        // Act
        boolean bookFound = bookRepo.existsByTitle(book.getTitle());

        // Assert
        Assertions.assertThat(bookFound).isTrue();
    }

    @Test
    public void BookRepo_UpdateBook_ReturnBookNotNull() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        author = authorRepo.save(author);

        Book book = Book.builder()
                .title("Computer Networks")
                .author(author).build();

        bookRepo.save(book);

        Book bookSave = bookRepo.findById(book.getId()).get();
        bookSave.setTitle("Calculus II");

        // Act
        Book updatedBook = bookRepo.save(bookSave);

        // Assert
        Assertions.assertThat(updatedBook.getTitle()).isNotNull();
        Assertions.assertThat(updatedBook.getTitle()).isEqualTo("Calculus II");
    }

    @Test
    public void BookRepo_DeleteById_ReturnBookIsEmpty() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        author = authorRepo.save(author);

        Book book = Book.builder()
                .title("Computer Networks")
                .author(author).build();

        bookRepo.save(book);

        // Act
        bookRepo.deleteById(book.getId());
        Optional<Book> bookReturn = bookRepo.findById(book.getId());

        // Assert
        Assertions.assertThat(bookReturn).isEmpty();
    }
}
