package com.todo.demo.exception;

public class PersonExistsException extends RuntimeException{
    public PersonExistsException(String msg)
    {
        super(msg);
    }
}
