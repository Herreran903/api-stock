package com.api_stock.stock.common.exception;

import com.api_stock.stock.category.domain.exception.ExceptionMessage;
import com.api_stock.stock.category.domain.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidParameterException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class AdviceHandler {

    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> handleCategoryAlreadyExistException(CategoryAlreadyExistException ex, WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(details, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryNotValidFieldException.class)
    public ResponseEntity<ExceptionDetails> handleMaxLengthExceededException(CategoryNotValidFieldException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDetails> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                String.format(ExceptionMessage.INVALID_TYPE_PARAM,
                        ex.getName(),
                        Objects.requireNonNull(ex.getRequiredType()).getSimpleName()),
                "",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotValidParameterException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidParameterException(CategoryNotValidParameterException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now()
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
            message = String.format(ExceptionMessage.INVALID_TYPE_PARAM,
                    Objects.requireNonNull(fieldName),
                    Objects.requireNonNull(requiredType));
        }

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message.isEmpty() ? ExceptionMessage.BAD_JSON_CATEGORY : message,
                "",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining(", "));

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessages,
                "",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDetails> handleConstraintViolationException(ConstraintViolationException ex) {

        String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessage,
                "",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(details);
    }
}
