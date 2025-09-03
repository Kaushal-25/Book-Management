package com.selfProjectbook.SelfProjecttwoBook.repository;

import com.selfProjectbook.SelfProjecttwoBook.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorContainingIgnoreCase(String author);

}