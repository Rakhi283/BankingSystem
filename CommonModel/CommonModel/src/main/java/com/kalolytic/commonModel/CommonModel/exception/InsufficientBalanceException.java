package com.kalolytic.commonModel.CommonModel.exception;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(String message){
        super(message);
    }
}
