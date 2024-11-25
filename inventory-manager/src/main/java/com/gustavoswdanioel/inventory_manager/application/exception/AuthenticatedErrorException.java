package com.gustavoswdanioel.inventory_manager.application.exception;

public class AuthenticatedErrorException extends RuntimeException{
    public AuthenticatedErrorException(String message){
        super(message);
    }
}
