package com.selfProjectbook.SelfProjecttwoBook.dto;

import lombok.Data;

@Data
public class AddBookRequestDto {
    private String title;
    private String author;
    private double price;

    private Long categoryId;
}
