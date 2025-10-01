package com.tw.finseta.payment.exception;

import com.tw.finseta.payment.model.Error;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Exception thrown when validation on Payment objects fails.
 *
 * <p>Contains a list of error messages describing validation rule violations.
 * Used for propagating error responses in a structured manner in REST APIs.</p>
 *
 * @author Ranga Raju
 */
@Getter
@ToString(callSuper = true)
public class ValidationException extends RuntimeException {

    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        super("Payment validation failed");
        this.errors = errors;
    }
}
