package com.practice.libraryservice.exception;

public class BookNotFoundException extends Exception{

    public BookNotFoundException(int id) {
        super("Book not found with id : "+id);
    }
}
