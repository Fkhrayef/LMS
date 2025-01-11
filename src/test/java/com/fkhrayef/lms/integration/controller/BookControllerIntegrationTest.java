package com.fkhrayef.lms.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkhrayef.lms.dto.BookDto;
import com.fkhrayef.lms.integration.config.BaseIntegrationTest;
import com.fkhrayef.lms.model.Author;
import com.fkhrayef.lms.repo.AuthorRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepo authorRepo;

    private Author author;

    @BeforeEach
    void setUp() {
        setupTestUsers();
        authorRepo.deleteAll();

        author = Author.builder()
                .name("Test Author")
                .build();
        author = authorRepo.save(author);
    }

    @Test
    void whenCreateBook_thenSuccess() throws Exception {
        BookDto bookDto = BookDto.builder()
                .title("Test Book")
                .authorName(author.getName())
                .build();

        mockMvc.perform(post("/books")
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("USER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(bookDto.getTitle()))
                .andExpect(jsonPath("$.authorName").value(bookDto.getAuthorName()));
    }

    @Test
    void whenGetBooks_thenSuccess() throws Exception {
        mockMvc.perform(get("/books")
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.totalElements").exists());
    }

    @Test
    void whenSearchBooks_thenSuccess() throws Exception {
        // First create a book
        BookDto bookDto = BookDto.builder()
                .title("Test Book")
                .authorName(author.getName())
                .build();

        mockMvc.perform(post("/books")
                .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("USER"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)));

        mockMvc.perform(get("/books/search")
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("USER"))
                        .param("title", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(bookDto.getTitle()));
    }
}