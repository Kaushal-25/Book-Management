package com.selfProjectbook.SelfProjecttwoBook.mapper;

import com.selfProjectbook.SelfProjecttwoBook.dto.AddBookRequestDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.BookResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.CategoryResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.entity.Book;
import com.selfProjectbook.SelfProjecttwoBook.entity.Category;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class BookMapper {

    // entity -> dto
    public static BookResponseDto entityToDto(Book book){
        return BookResponseDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .categoryName(
                        book.getCategory() !=null ? book.getCategory().getName(): null
                )
                .build();
    }

    // dto-> entity
    public static Book dtoToEntity(AddBookRequestDto addBookRequestDto){
        Book book = new Book();
        book.setTitle(addBookRequestDto.getTitle());
        book.setAuthor(addBookRequestDto.getAuthor());
        book.setPrice(Double.parseDouble(String.valueOf(addBookRequestDto.getPrice())));
        return book;
    }

    // updating
    public static Book updateEntity(AddBookRequestDto addBookRequestDto, Book book){
        book.setTitle(addBookRequestDto.getTitle());
        book.setAuthor(addBookRequestDto.getAuthor());
        book.setPrice(Double.parseDouble(String.valueOf(addBookRequestDto.getPrice())));
        return book;
    }

    // category entity to dto
    public static CategoryResponseDto catEntityToDto(Category category){
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
