package com.fkhrayef.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BookDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
