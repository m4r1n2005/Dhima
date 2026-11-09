package org.example.app.domain;

public class MediaNotFoundException extends Exception{
    public MediaNotFoundException(String message){
        super(message);
    }
}
