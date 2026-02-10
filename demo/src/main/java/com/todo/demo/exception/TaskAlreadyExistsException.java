package com.todo.demo.exception;

public class TaskAlreadyExistsException extends RuntimeException{
    public TaskAlreadyExistsException(String msg)
    {
        super(msg);
    }
}
