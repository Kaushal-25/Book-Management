package com.selfProjectbook.SelfProjecttwoBook.service;


import com.selfProjectbook.SelfProjecttwoBook.dto.AddBookRequestDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.BookResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.CategoryResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.entity.Category;

import java.util.List;
import java.util.Map;


public interface BookService {

    List<BookResponseDto> getAllBooks();

    BookResponseDto getBookById(Long id);

    void addNewBook(AddBookRequestDto addBookRequestDto);

    BookResponseDto updateBook(AddBookRequestDto addBookRequestDto, Long id);

    BookResponseDto updatePartialBook(Long id, Map<String, Object> updates);

    void deleteBook(Long id);

    List<BookResponseDto> getByAuthor(String author);

    // generic filter
    List<BookResponseDto> search(String author, String title, Double price);

    List<CategoryResponseDto> getAllCategory();
}
