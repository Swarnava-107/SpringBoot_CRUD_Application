package org.example.CRUDSpringBootDemo.exception;

public class DuplicateResourceException extends RuntimeException{

    public DuplicateResourceException(String message){
        super(message);
    }
}
