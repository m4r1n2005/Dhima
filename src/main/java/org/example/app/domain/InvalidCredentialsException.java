package org.example.app.domain;

public class InvalidCredentialsException extends Exception{

    public InvalidCredentialsException(String message){
        super(message);
    }
}
