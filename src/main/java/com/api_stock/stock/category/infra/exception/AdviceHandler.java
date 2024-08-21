package com.api_stock.stock.category.infra.exception;

import com.api_stock.stock.category.app.exception.ex.InvalidParameterException;
import com.api_stock.stock.category.domain.exception.ex.EmptyFieldException;
import com.api_stock.stock.category.domain.exception.ex.MaxLengthExceededException;
import com.api_stock.stock.category.infra.exception.ex.CategoryAlreadyExistException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@ControllerAdvice
public class AdviceHandler {

    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> handleCategoryAlreadyExistException(CategoryAlreadyExistException ex, WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(details, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MaxLengthExceededException.class)
    public ResponseEntity<ExceptionDetails> handleMaxLengthExceededException(MaxLengthExceededException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                ""
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ExceptionDetails> handleEmptyFieldException(EmptyFieldException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                ""
        );
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDetails> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                String.format("The parameter '%s' must be of type '%s'.", ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()),
                ""
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidParameterException(InvalidParameterException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                ""
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDetails> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String fieldName;
        String requiredType;
        String message = "";

        if (ex.getCause() instanceof MismatchedInputException mie) {
            fieldName = !mie.getPath().isEmpty() ? mie.getPath().get(0).getFieldName() : "Unknown";
            requiredType = mie.getTargetType() != null ? mie.getTargetType().getSimpleName() : "Unknown";
            message = String.format("The parameter '%s' must be of type '%s'",
                    Objects.requireNonNull(fieldName),
                    Objects.requireNonNull(requiredType));
        }

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                message.isEmpty() ? "Invalid request format: Please check your JSON syntax" : message,
                ""
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }
}
