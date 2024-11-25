package com.gustavoswdanioel.inventory_manager.application.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
