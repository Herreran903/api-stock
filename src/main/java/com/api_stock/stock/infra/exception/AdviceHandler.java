package com.api_stock.stock.infra.exception;

import com.api_stock.stock.domain.brand.exception.ex.BrandNotFoundByIdException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidParameterException;
import com.api_stock.stock.domain.category.exception.ex.CategoriesNotFoundByIdsException;
import com.api_stock.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidParameterException;
import com.api_stock.stock.domain.brand.exception.ex.BrandAlreadyExistException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidFieldException;
import com.api_stock.stock.domain.product.exception.ex.ProductNotFoundByIdException;
import com.api_stock.stock.domain.product.exception.ex.ProductNotValidFieldException;
import com.api_stock.stock.domain.product.exception.ex.ProductNotValidParameterException;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.api_stock.stock.domain.util.GlobalExceptionMessage.INVALID_TOKEN;

@ControllerAdvice
public class AdviceHandler {
    //Category
    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> handleCategoryAlreadyExistException(CategoryAlreadyExistException ex, WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now(),
                null
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
                LocalDateTime.now(),
                null
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
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriesNotFoundByIdsException.class)
    public ResponseEntity<ExceptionDetails> handleCategoriesNotFoundByIdsException(CategoriesNotFoundByIdsException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                ex.getMissingIds().toString(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    //Brand
    @ExceptionHandler(BrandAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> handleCategoryAlreadyExistException(BrandAlreadyExistException ex, WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BrandNotValidFieldException.class)
    public ResponseEntity<ExceptionDetails> handleMaxLengthExceededException(BrandNotValidFieldException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BrandNotValidParameterException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidParameterException(BrandNotValidParameterException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BrandNotFoundByIdException.class)
    public ResponseEntity<ExceptionDetails> handleCategoriesNotFoundByIdsException(BrandNotFoundByIdException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    //Product
    @ExceptionHandler(ProductNotFoundByIdException.class)
    public ResponseEntity<ExceptionDetails> handleProductNotFoundByIdsException(ProductNotFoundByIdException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotValidFieldException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidParameterException(ProductNotValidFieldException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotValidParameterException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidParameterException(ProductNotValidParameterException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    //Auth
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDetails> handleAccessDeniedException(AccessDeniedException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                GlobalExceptionMessage.BAD_ROLE,
                "",
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(details);
    }

    //Jwt
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionDetails> handleJwtException(JwtException ex) {
        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                INVALID_TOKEN,
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(details);
    }

    //General
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDetails> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<FieldErrorsDetails> errorsDetails = new ArrayList<>();

        if (ex.getCause() instanceof MismatchedInputException mie) {
            String fieldName = !mie.getPath().isEmpty() ? mie.getPath().get(0).getFieldName() : "Unknown";
            String requiredType = mie.getTargetType() != null ? mie.getTargetType().getSimpleName() : "Unknown";
            String message = String.format(GlobalExceptionMessage.INVALID_TYPE_PARAM,
                    Objects.requireNonNull(fieldName),
                    Objects.requireNonNull(requiredType));

            errorsDetails.add(new FieldErrorsDetails(fieldName, message));
        }

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                GlobalExceptionMessage.INVALID_JSON,
                "",
                LocalDateTime.now(),
                errorsDetails
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FieldErrorsDetails> errorsDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FieldErrorsDetails(fieldError.getField(), fieldError.getDefaultMessage()))
                .distinct()
                .toList();

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                GlobalExceptionMessage.INVALID_OBJECT,
                "",
                LocalDateTime.now(),
                errorsDetails
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDetails> handleConstraintViolationException(ConstraintViolationException ex) {

        List<FieldErrorsDetails> errorsDetails = ex.getConstraintViolations().stream()
                .map(constraintViolation -> {
                    String fieldName = constraintViolation.getPropertyPath().toString();
                    fieldName = fieldName.contains(".")
                            ? fieldName.substring(fieldName.lastIndexOf('.') + 1)
                            : fieldName;
                    return new FieldErrorsDetails(fieldName, constraintViolation.getMessage());
                })
                .toList();

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                GlobalExceptionMessage.INVALID_PARAMETERS,
                "",
                LocalDateTime.now(),
                errorsDetails
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }
}
