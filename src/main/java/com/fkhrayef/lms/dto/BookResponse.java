package com.fkhrayef.lms.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookResponse {
    private List<BookDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

}
