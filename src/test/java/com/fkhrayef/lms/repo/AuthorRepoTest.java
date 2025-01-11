package com.fkhrayef.lms.repo;

import com.fkhrayef.lms.model.Author;
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
public class AuthorRepoTest {

    @Autowired
    private AuthorRepo authorRepo;

    @Test
    public void AuthorRepo_SaveAll_ReturnSavedAuthor() {

        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();

        // Act
        Author savedAuthor = authorRepo.save(author);

        // Assert
        Assertions.assertThat(savedAuthor).isNotNull();
        Assertions.assertThat(savedAuthor.getId()).isGreaterThan(0);
    }

    @Test
    public void AuthorRepo_GetAll_ReturnMoreThanOneAuthor() {
        // Arrange
        Author author1 = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();
        Author author2 = Author.builder()
                .name("Mark S. Henry")
                .build();

        authorRepo.save(author1);
        authorRepo.save(author2);

        // Act
        List<Author> authorList = authorRepo.findAll();

        // Assert
        Assertions.assertThat(authorList).isNotNull();
        Assertions.assertThat(authorList.size()).isEqualTo(2);
    }

    @Test
    public void AuthorRepo_FindById_ReturnAuthor() {

        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();

        authorRepo.save(author);

        // Act
        Author authorFound = authorRepo.findById(author.getId()).get();

        // Assert
        Assertions.assertThat(authorFound).isNotNull();
    }

    @Test
    public void BookRepo_UpdateAuthor_ReturnAuthorNotNull() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();

        authorRepo.save(author);

        Author authorSave = authorRepo.findById(author.getId()).get();
        authorSave.setName("Faisal");

        // Act
        Author updatedAuthor = authorRepo.save(authorSave);

        // Assert
        Assertions.assertThat(updatedAuthor.getName()).isNotNull();
        Assertions.assertThat(updatedAuthor.getName()).isEqualTo("Faisal");
    }

    @Test
    public void AuthorRepo_DeleteById_ReturnAuthorIsEmpty() {
        // Arrange
        Author author = Author.builder()
                .name("Andrew S. Tanenbaum")
                .build();

        authorRepo.save(author);

        // Act
        authorRepo.deleteById(author.getId());
        Optional<Author> authorReturn = authorRepo.findById(author.getId());

        // Assert
        Assertions.assertThat(authorReturn).isEmpty();
    }
}
