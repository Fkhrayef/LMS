package com.fkhrayef.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkhrayef.lms.dto.BookDto;
import com.fkhrayef.lms.dto.BookResponse;
import com.fkhrayef.lms.model.Author;
import com.fkhrayef.lms.security.jwt.JwtUtils;
import com.fkhrayef.lms.security.services.UserDetailsServiceImpl;
import com.fkhrayef.lms.service.BookService;
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

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private Author author;
    private BookDto bookDto;

    @BeforeEach
    public void init() {
        author = Author.builder().name("Andrew S. Tanenbaum").build();
        bookDto = BookDto.builder().title("Computer Networks").authorName("Andrew S. Tanenbaum").build();
    }

    @Test
    public void BookController_AddBook_ReturnAddedBook() throws Exception {
        given(bookService.addBook(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(bookDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorName", CoreMatchers.is(bookDto.getAuthorName())));
    }

    @Test
    public void BookController_GetBooks_ReturnResponseDto() throws Exception {
        BookResponse responseDto = BookResponse.builder().content(Arrays.asList(bookDto)).pageNo(1).pageSize(10).last(true).build();
        when(bookService.getAllBooks(1, 10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/books?pageNo=1&pageSize=10")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void BookController_GetBookById_ReturnBookDto() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.ofNullable(bookDto));

        ResultActions response = mockMvc.perform(get("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(bookDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorName", CoreMatchers.is(bookDto.getAuthorName())));
    }

    @Test
    public void BookController_UpdateBook_ReturnBookDto() throws Exception {
        when(bookService.updateBook(bookDto)).thenReturn(bookDto);

        ResultActions response = mockMvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(bookDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorName", CoreMatchers.is(bookDto.getAuthorName())));
    }

    @Test
    public void BookController_DeleteBook_ReturnNoContent() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(bookDto));
        doNothing().when(bookService).deleteBook(1L);

        ResultActions response = mockMvc.perform(delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void BookController_DeleteNonExistentBook_ReturnNotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
