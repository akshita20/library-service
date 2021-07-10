package com.practice.libraryservice.controller;

import com.practice.libraryservice.client.feign.BookServiceClient;
import com.practice.libraryservice.config.SwaggerConfig;
import com.practice.libraryservice.entity.Book;
import com.practice.libraryservice.exception.BookNotFoundException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = { SwaggerConfig.Library_TAG})
public class LibraryController {

    @Autowired
    BookServiceClient bookServiceClient;

    /**
     * Get all Books list.
     *
     * @return the list
     */
    @ApiOperation(value = "View list of books", response = List.class)
    @GetMapping("/library/books")
    public List<Book> getBooks() {
        return bookServiceClient.getBooks();
    }

    /**
     * Get Book by id.
     *
     * @param bookId the Book id
     * @return the Book by id
     */
    @ApiOperation(value = "Retrieves a book by the given ID.", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Book not found. ")
    })
    @GetMapping("library/books/{id}")
    public ResponseEntity<Book> getBooksById(
            @ApiParam(name="id", value = "The ID of the book.", required = true)
            @PathVariable(value = "id") int bookId) throws BookNotFoundException {
        return bookServiceClient.getBookById(bookId);
    }
}
