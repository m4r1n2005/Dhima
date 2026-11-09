package org.example.app.domain;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message){
        super(message);
    }
}
