package com.example.demo.Model.exception;

public class NotGoodException extends RuntimeException{
    public NotGoodException(String id) {
        super(String.format("Manufacturer with id: %s was not found", id));
    }
}
