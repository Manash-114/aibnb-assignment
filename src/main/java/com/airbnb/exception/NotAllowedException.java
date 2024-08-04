package com.airbnb.exception;

public class NotAllowedException extends RuntimeException{
    public NotAllowedException(String message){
        super(message);
    }
}
