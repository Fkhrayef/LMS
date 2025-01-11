package com.fkhrayef.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkhrayef.lms.dto.AuthorDto;
import com.fkhrayef.lms.dto.BookDto;
import com.fkhrayef.lms.model.Author;
import com.fkhrayef.lms.model.Book;
import com.fkhrayef.lms.security.jwt.JwtUtils;
import com.fkhrayef.lms.security.services.UserDetailsServiceImpl;
import com.fkhrayef.lms.service.AuthorService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void AuthorController_GetAuthorById_ReturnAuthor() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.ofNullable(author));

        ResultActions response = mockMvc.perform(get("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(author.getName())));
    }

    @Test
    public void AuthorController_GetNonExistentAuthorById_ReturnAuthor() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void AuthorController_UpdateAuthor_ReturnAuthor() throws Exception {
        author.setId(1L);
        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));
        when(authorService.updateAuthor(author)).thenReturn(author);

        ResultActions response = mockMvc.perform(put("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(author.getName())));
    }
    @Test
    public void AuthorController_UpdateNonExistentAuthor_ReturnAuthor() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.empty());
        when(authorService.updateAuthor(author)).thenReturn(author);

        ResultActions response = mockMvc.perform(put("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void AuthorController_AddAuthor_ReturnAuthor() throws Exception {
        given(authorService.addAuthor(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(author.getName())));
    }

    @Test
    public void AuthorController_DeleteAuthor_ReturnNoContent() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));
        doNothing().when(authorService).deleteAuthor(1L);

        ResultActions response = mockMvc.perform(delete("/authors/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    public void AuthorController_DeleteNonExistentAuthor_ReturnNoContent() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.empty());
        doNothing().when(authorService).deleteAuthor(1L);

        ResultActions response = mockMvc.perform(delete("/authors/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
