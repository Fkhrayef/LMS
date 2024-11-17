package com.fkhrayef.lms.repo;

import com.fkhrayef.lms.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b " +
            "JOIN b.author a WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT( '%', :title, '%' )) OR " +
            "LOWER(a.name) LIKE LOWER(CONCAT( '%', :authorName, '%'))")
    List<Book> findByTitleOrAuthor(@Param("title") String title, @Param("authorName") String authorName);
}