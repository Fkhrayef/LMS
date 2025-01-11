package com.fkhrayef.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class AuthorDto {
    private Long id;
//    These validation annotations are just for "looks" it doesn't work
//    because AuthorDto just used in GetAll Books and not in POST or Update.
//    The real annotation is in the models: Author.java (Code smell)
    @NotBlank(message = "Author name is mandatory")
    @Size(min = 2, max = 100, message = "Author name should be between 2 and 100 characters")
    private String name;
    private List<BookDto> books;

    public AuthorDto(Long id, String name, List<BookDto> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }
}
