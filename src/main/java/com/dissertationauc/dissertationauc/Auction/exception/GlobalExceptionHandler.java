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

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InsufficentFundsExcepion.class)
    public GlobalErrorResponse handleInsufficientFundsException(InsufficentFundsExcepion e){
        return new GlobalErrorResponse("Insufficient funds.", e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidBidderException.class)
    public GlobalErrorResponse handleInvalidBidderException(InvalidBidderException e){
        return new GlobalErrorResponse("Owner of an item cannot set a bid on their own item.", e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuctionAlreadyExistsException.class)
    public GlobalErrorResponse handleAuctionAlreadyExistsException(AuctionAlreadyExistsException e){
        return new GlobalErrorResponse("Auction Already Exists.", e.getLocalizedMessage());
    }



    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemAlreadyExistsException.class)
    public GlobalErrorResponse handleItemAlreadyExistsException(ItemAlreadyExistsException e){
        return new GlobalErrorResponse("Item Already Exists.", e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUserNameAndPasswordException.class)
    public GlobalErrorResponse handleInvalidUserNameAndPasswordException(InvalidUserNameAndPasswordException e){
        return new GlobalErrorResponse("Invalid Username or Password", e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuctionClosedException.class)
    public GlobalErrorResponse handleAuctionClosedException(AuctionClosedException e){
        return new GlobalErrorResponse("The Auction is already closed.", e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidItemException.class)
    public GlobalErrorResponse handleInvalidItemException(InvalidItemException e){
        return new GlobalErrorResponse("ITEM.INVALID", e.getLocalizedMessage());
    }


}
