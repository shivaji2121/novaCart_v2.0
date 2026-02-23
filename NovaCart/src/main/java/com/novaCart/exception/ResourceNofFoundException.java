package com.novaCart.exception;

public class ResourceNofFoundException extends RuntimeException{
    public ResourceNofFoundException(String message) {
        super(message);
    }
}