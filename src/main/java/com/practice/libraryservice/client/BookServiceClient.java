package com.practice.libraryservice.client;

import com.practice.libraryservice.entity.Book;
import com.practice.libraryservice.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class BookServiceClient {

    @Autowired
    RestTemplate restTemplate;

    private final String bookUrl = "http://book-service/books";

    @GetMapping("/library/books")
    public List<Book> getBooks() {
        Book[] bookArray = restTemplate.getForObject(bookUrl,Book[].class);
        return Arrays.asList(bookArray);
    }

    @GetMapping(value = "library/books/{id}")
    public ResponseEntity<Book> getBookById(
            @PathVariable(value = "id") int bookId) throws BookNotFoundException {
        ResponseEntity<Book> responseEntity ;
        try {
            responseEntity = restTemplate.getForEntity(bookUrl + "/" + bookId, Book.class);
        }catch(HttpClientErrorException e){
            throw new BookNotFoundException(bookId);
        }
        return responseEntity;
    }

    @PostMapping(value = "library/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return restTemplate.postForEntity(bookUrl,book,Book.class);
    }

    @PutMapping(value = "library/books/{id}")
    public void updateBook(@PathVariable(value = "id") Integer bookId,
                           @RequestBody Book book) {
        restTemplate.put(bookUrl+"/"+bookId,book);
    }

    @DeleteMapping(value = "library/books/{id}")
    public void deleteBook(@PathVariable(value = "id") Integer bookId) {
        restTemplate.delete(bookUrl+"/"+bookId);
    }
}
