package com.fkhrayef.lms.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {
    private Long id;
    private String name;
    private List<BookDto> books;

    public AuthorDto(Long id, String name, List<BookDto> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }
}
