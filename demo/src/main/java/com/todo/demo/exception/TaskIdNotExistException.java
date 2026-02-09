package com.todo.demo.exception;

public class TaskIdNotExistException extends RuntimeException{
    public TaskIdNotExistException(String msf)
    {
        super(msf);
    }
}
