package com.selfProjectbook.SelfProjecttwoBook.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookResponseDto {
    private String title;
    private double price;
    private String author;

    private String categoryName;
}
