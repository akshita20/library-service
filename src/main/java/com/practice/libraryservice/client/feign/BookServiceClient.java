package com.practice.libraryservice.client.feign;

import com.practice.libraryservice.entity.Book;
import com.practice.libraryservice.exception.BookNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("book-service")
public interface BookServiceClient {

    @GetMapping("/books")
    public List<Book> getBooks();

    @GetMapping(value = "/books/{id}")
    public ResponseEntity<Book> getBookById(
            @PathVariable(value = "id") int bookId) throws BookNotFoundException;
}
