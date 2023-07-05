package com.dissertationauc.dissertationauc.Auction.exception;

import com.dissertationauc.dissertationauc.Auction.exception.dataExceptions.GlobalErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public GlobalErrorResponse handleUserNotFoundException(UserNotFoundException e) {



        return new GlobalErrorResponse("USER NOT FOUND", e.getLocalizedMessage());


    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public GlobalErrorResponse handleUserAlreadyExistsException(UserAlreadyExistsException e) {



        return new GlobalErrorResponse("USER ALREADY EXISTS", e.getLocalizedMessage());


    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUserNameException.class)
    public GlobalErrorResponse handleInvalidUserNameException(InvalidUserNameException e){
        return new GlobalErrorResponse("Username was not entered.", e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidEmailException.class)
    public GlobalErrorResponse handleInvalidEmailException(InvalidEmailException e){
        return new GlobalErrorResponse("Email was not entered.", e.getLocalizedMessage());
    }

}
