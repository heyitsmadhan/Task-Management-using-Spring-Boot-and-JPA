package com.todo.demo.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonExistsException.class)
    public ResponseEntity<ApiError>personNameExistsException(PersonExistsException ex, HttpServletRequest request)
    {
        ApiError apiError= new ApiError();
        apiError.setStatusCode(HttpStatus.BAD_REQUEST.value());
        apiError.setErrorMsg(ex.getMessage());
        apiError.setUri(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    @ExceptionHandler(TaskIdNotExistException.class)
    public ResponseEntity<ApiError>taskIdNotExistsException(TaskIdNotExistException ex,HttpServletRequest request)
    {
        ApiError apiError= new ApiError();
        apiError.setStatusCode(HttpStatus.BAD_REQUEST.value());
        apiError.setErrorMsg(ex.getMessage());
        apiError.setUri(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
