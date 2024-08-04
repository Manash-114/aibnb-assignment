package com.airbnb.exception;

public class ResourceNotFoundExcaption extends RuntimeException{
    public ResourceNotFoundExcaption(String message){
        super(message);
    }
}
