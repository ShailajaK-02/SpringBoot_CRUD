package com.company.exception;

//This is custome exception
public class DeveloperNotFoundException extends RuntimeException
{
    public DeveloperNotFoundException(String message) {
        super(message);
    }
}
