package com.practice.libraryservice.client;

import com.practice.libraryservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserServiceClient {

    @Autowired
    RestTemplate restTemplate;

    private final String userUrl = "http://user-service/users";

    @GetMapping("/library/users")
    public List<User> getUsers() {
        User[] userArray = restTemplate.getForObject(userUrl,User[].class);
        return Arrays.asList(userArray);
    }

    @GetMapping(value = "library/users/{id}")
    public ResponseEntity<User> getUserById(
    @PathVariable(value = "id") int userId) {
        return restTemplate.getForEntity(userUrl +"/"+userId,User.class);
    }

    @PostMapping(value = "library/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return restTemplate.postForEntity(userUrl,user,User.class);
    }

    @PutMapping(value = "library/users/{id}")
    public void updateUser(@PathVariable(value = "id") Integer userId,
            @RequestBody User user) {
        restTemplate.put(userUrl +"/"+userId,user);
    }

    @DeleteMapping(value = "library/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Integer userId) {
        restTemplate.delete(userUrl +"/"+userId);
    }
}
