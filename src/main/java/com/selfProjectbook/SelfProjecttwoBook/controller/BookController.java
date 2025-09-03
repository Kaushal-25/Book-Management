package com.selfProjectbook.SelfProjecttwoBook.controller;

import com.selfProjectbook.SelfProjecttwoBook.dto.AddBookRequestDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.BookResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.CategoryResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<BookResponseDto>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/all/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> addNewBook(
             @RequestBody AddBookRequestDto addBookRequestDto
            ){
        bookService.addNewBook(addBookRequestDto);
        return ResponseEntity.ok("book added successfully");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(
            @PathVariable Long id, @RequestBody AddBookRequestDto addBookRequestDto){
        return ResponseEntity.ok(bookService.updateBook(addBookRequestDto,id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<BookResponseDto> partialUpdateBook(
            @PathVariable Long id, @RequestBody Map<String, Object> updates
            ){
        return ResponseEntity.ok(bookService.updatePartialBook(id,updates));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Successfully deleted ");
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/author")
    public ResponseEntity<List<BookResponseDto>> getByAuthor(
            @RequestParam String author){
        return ResponseEntity.ok(bookService.getByAuthor(author));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDto>> searchBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double price) {

        return ResponseEntity.ok(bookService.search(author, title, price));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategory(){
        return ResponseEntity.ok(bookService.getAllCategory());
    }

}
