package com.tw.finseta.payment.advice;

import com.tw.finseta.payment.exception.ValidationException;
import com.tw.finseta.payment.model.BadRequest;
import com.tw.finseta.payment.model.Error;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * REST controller advice that handles validation and deserialization errors for the payment API.
 *
 * <p>Maps {@link ValidationException} and deserialization exceptions to HTTP error responses, ensuring
 * clients receive meaningful feedback for invalid requests. Utilizes Spring's @RestControllerAdvice.</p>
 *
 * @author Ranga Raju
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LogManager.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BadRequest> handleValidationException(ValidationException ex) {
        logger.error("ValidationException:", ex);
        BadRequest badRequest = new BadRequest();
        badRequest.setErrors(ex.getErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BadRequest> handleNotReadableException(HttpMessageNotReadableException ex) {
        logger.error("HttpMessageNotReadableException:", ex);
        BadRequest badRequest = new BadRequest();
        badRequest.setErrors(List.of(new Error().message("Counterparty type must be SORT_CODE_ACCOUNT_NUMBER")));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequest> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error("MethodArgumentNotValidException:", ex);
        BadRequest badRequest = new BadRequest();
        badRequest.setErrors(List.of(new Error().message(ex.getMessage())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Unhandled Exception:", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
