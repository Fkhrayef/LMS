package com.fkhrayef.lms.dto;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String authorName;

    public BookDto(Long id, String title, String authorName) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
    }
}
