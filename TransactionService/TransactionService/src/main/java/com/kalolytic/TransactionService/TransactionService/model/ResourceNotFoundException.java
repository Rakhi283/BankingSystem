package com.kalolytic.TransactionService.TransactionService.model;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s) {
        super(s);
    }
}
