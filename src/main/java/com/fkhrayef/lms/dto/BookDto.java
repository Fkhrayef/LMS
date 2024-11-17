package com.fkhrayef.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDto {
    private Long id;
    @NotBlank(message = "Title is mandatory")
    @Size(min = 2, max = 255, message = "Title should be between 2 and 255 characters")
    private String title;
    @NotNull(message = "Author cannot be null")
    private String authorName;

    public BookDto(Long id, String title, String authorName) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
    }
}
