package com.api_stock.stock.category.infra.exception;

import com.api_stock.stock.category.domain.exception.ex.EmptyFieldException;
import com.api_stock.stock.category.domain.exception.ex.MaxLengthExceededException;
import com.api_stock.stock.category.infra.exception.ex.CategoryAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AdviceHandler {

    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> handleCategoryAlreadyExistException(CategoryAlreadyExistException e, WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(details, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MaxLengthExceededException.class)
    public ResponseEntity<ExceptionDetails> handleMaxLengthExceededException(MaxLengthExceededException e) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                ""
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ExceptionDetails> handleEmptyFieldException(EmptyFieldException e) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                ""
        );
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
