package com.practice.libraryservice.client.resttemplate;

import com.practice.libraryservice.client.feign.BookServiceClient;
import com.practice.libraryservice.entity.Book;
import com.practice.libraryservice.entity.Library;
import com.practice.libraryservice.entity.User;
import com.practice.libraryservice.exception.BookNotFoundException;
import com.practice.libraryservice.exception.UserNotFoundException;
import com.practice.libraryservice.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class UserServiceClient {

    @Autowired
    LibraryRepository libraryRepository;

//    @Autowired
//    BookServiceClient bookServiceClient;

    @Autowired
    BookServiceClient bookServiceClient;

    @Autowired
    RestTemplate restTemplate;

    private final String userUrl = "http://user-service/users";

    @GetMapping("/library/users")
    public List<User> getUsers() {
        User[] userArray = restTemplate.getForObject(userUrl, User[].class);
        return Arrays.asList(userArray);
    }

    @GetMapping(value = "library/users/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable(value = "id") int userId) throws UserNotFoundException {
        User user = null;
        try {
            user = restTemplate.getForObject(userUrl + "/" + userId, User.class);
            List<Integer> bookIdList = libraryRepository.findByUserId(userId).orElse(null).getBookIds();
            if (CollectionUtils.isEmpty(bookIdList)) {
                user.setIssuedBooks(Collections.emptyList());
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            List<Book> bookList = new ArrayList<>(bookIdList.size());
            for (Integer bookId : bookIdList) {
                ResponseEntity<Book> book = bookServiceClient.getBookById(bookId);
                bookList.add(book.getBody());
            }
            user.setIssuedBooks(bookList);
        } catch (HttpClientErrorException e) {
            throw new UserNotFoundException(userId);
        } catch (BookNotFoundException e) {
            user.setIssuedBooks(Collections.emptyList());
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "library/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        ResponseEntity<User> createdUser =  restTemplate.postForEntity(userUrl, user, User.class);
        Library library = Library.builder().userId(createdUser.getBody().getId()).build();
        libraryRepository.save(library);
        return createdUser;
    }

    @PutMapping(value = "library/users/{id}")
    public void updateUser(@PathVariable(value = "id") Integer userId,
                           @RequestBody User user) {
        restTemplate.put(userUrl + "/" + userId, user);
    }

    @DeleteMapping(value = "library/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
        restTemplate.delete(userUrl + "/" + userId);
        Library library = libraryRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
        libraryRepository.delete(library);
    }

    @PostMapping(value = "library/users/{user_id}/books/{book_id}")
    public void issueBookToUser(
            @PathVariable(value = "user_id") int userId, @PathVariable(value = "book_id") int bookId) throws UserNotFoundException {

        Library library = libraryRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Integer> bookIds = library.getBookIds();
        bookIds.add(bookId);
        library.setToBookIds(bookIds);
        libraryRepository.save(library);
    }

    @DeleteMapping(value = "library/users/{user_id}/books/{book_id}")
    public void deleteBookToUser(
            @PathVariable(value = "user_id") int userId, @PathVariable(value = "book_id") int bookId) throws UserNotFoundException {

        Library library = libraryRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Integer> bookIds = library.getBookIds();
        bookIds.remove(Integer.valueOf(bookId));
        library.setToBookIds(bookIds);
        libraryRepository.save(library);
    }
}
