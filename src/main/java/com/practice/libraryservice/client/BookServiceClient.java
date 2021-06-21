package com.practice.libraryservice.client;

import com.practice.libraryservice.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class BookServiceClient {

    @Autowired
    RestTemplate restTemplate;

    private String bookUrl = "http://book-service/books";

    @GetMapping("/library/books")
    public List<Book> getBooks() {
        Book[] bookArray = restTemplate.getForObject(bookUrl,Book[].class);
        return Arrays.asList(bookArray);
    }
}
