package com.kalolytic.commonModel.CommonModel.ExceptionHandler;

import com.kalolytic.commonModel.CommonModel.exception.*;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleResourceNotFound(ResourceNotFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>(ex.getMessage(),"FAILURE", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleGeneralException(Exception ex) {
        ResponseStructure<String> response = new ResponseStructure<>(ex.getMessage(),"FAILURE", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidInputException(InvalidInputException ex) {
        ResponseStructure<String> response = new ResponseStructure<>(ex.getMessage(),"FAILURE", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ResponseStructure<String>> handleInsufficientBalance(InsufficientBalanceException ex) {
        ResponseStructure<String> response = new ResponseStructure<>(ex.getMessage(),"FAILURE", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleAccountNotFoundException(AccountNotFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>(ex.getMessage(),"FAILURE", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ResponseStructure<String>> handleTransactionException(TransactionException ex) {
        ResponseStructure<String> response = new ResponseStructure<>(ex.getMessage(),"FAILURE", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>(ex.getMessage(),"FAILURE", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(DuplicateElementFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleDuplicateElementFoundException(DuplicateElementFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>(ex.getMessage(),"FAILURE", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
