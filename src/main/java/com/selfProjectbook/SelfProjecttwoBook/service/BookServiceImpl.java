package com.selfProjectbook.SelfProjecttwoBook.service;

import com.selfProjectbook.SelfProjecttwoBook.dto.AddBookRequestDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.BookResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.CategoryResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.entity.Book;
import com.selfProjectbook.SelfProjecttwoBook.entity.Category;
import com.selfProjectbook.SelfProjecttwoBook.mapper.BookMapper;
import com.selfProjectbook.SelfProjecttwoBook.repository.BookRepository;
import com.selfProjectbook.SelfProjecttwoBook.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<BookResponseDto> getAllBooks() {
        List<Book> allBook  = bookRepository.findAll();
        return allBook.stream()
                .map(BookMapper::entityToDto)
                .toList();
    }

    @Override
    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Book not found by this id"));
        return BookMapper.entityToDto(book);
    }

    @Override
    public void addNewBook(AddBookRequestDto addBookRequestDto) {
        Category category = categoryRepository.findById(addBookRequestDto.getCategoryId())
                .orElseThrow(()-> new IllegalArgumentException("Category not found"));

        Book newBook = BookMapper.dtoToEntity(addBookRequestDto);
        newBook.setCategory(category);
        bookRepository.save(newBook);
    }

    @Override
    public BookResponseDto updateBook(AddBookRequestDto addBookRequestDto, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Book with this id not found"));
        BookMapper.updateEntity(addBookRequestDto, book);
        bookRepository.save(book);
        return BookMapper.entityToDto(book);
    }

    @Override
    public BookResponseDto updatePartialBook(Long id, Map<String, Object> updates) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Book with this id not found"));
        updates.forEach((field, value)->{
            switch (field){
                case"title": {
                    book.setTitle((String) value);
                    return;
                }
                case "author":{
                    book.setAuthor((String) value);
                    return;
                }
                case "price":
                    if (value instanceof Number) {
                        book.setPrice(((Number) value).doubleValue());
                    } else if (value instanceof String) {
                        book.setPrice(Double.parseDouble((String) value));
                    }
                    return;
                default:
                    throw  new IllegalArgumentException("Field is not supported");
            }
        });
        bookRepository.save(book);
        return BookMapper.entityToDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Book not found with this id"));
        bookRepository.delete(book);
    }

    @Override
    public List<BookResponseDto> getByAuthor(String author) {
        List<Book> book = bookRepository.findByAuthorContainingIgnoreCase(author);
        if (book==null){
            throw new IllegalArgumentException("No book found with this author");
        }
        return  book.stream().map(BookMapper::entityToDto).toList();
    }

    // generic filter

    @Override
    public List<BookResponseDto> search(String author, String title, Double price) {
        List<Book> books = bookRepository.findAll();

        if (author!=null && !author.isEmpty()){
            books=books.stream()
                    .filter(b-> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .toList();
        }

        if (title!=null && !title.isEmpty()){
            books=books.stream()
                    .filter(b-> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .toList();
        }
        if (price != null) {
            books = books.stream()
                    .filter(b -> Double.compare(b.getPrice(), price) == 0)
                    .toList();
        }
        return books.stream()
                .map(BookMapper::entityToDto)
                .toList();
    }

    public List<CategoryResponseDto> getAllCategory(){
        List<Category> allCategory = categoryRepository.findAll();
        return allCategory.stream()
                .map(BookMapper::catEntityToDto)
                .toList();
    }
}
