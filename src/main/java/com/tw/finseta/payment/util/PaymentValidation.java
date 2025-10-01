package com.tw.finseta.payment.util;

import com.tw.finseta.payment.bo.AccountType;
import com.tw.finseta.payment.exception.ValidationException;
import com.tw.finseta.payment.model.Account;
import com.tw.finseta.payment.model.Error;
import com.tw.finseta.payment.model.Payment;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for validating Payment objects.
 * <p>
 * This class performs various validation checks on Payment instances,
 * including null checks, amount validation, currency code format,
 * and counterparty details validity.
 * <p>
 * If validation fails, a ValidationException containing detailed error messages is thrown.
 * This class is final and cannot be instantiated.
 *
 * @author Ranga Raju
 */
public final class PaymentValidation {

    /**
     * Validates the given Payment object.
     * <p>
     * Validation covers the following criteria:
     * <ul>
     *     <li>Payment object must not be null</li>
     *     <li>Amount must be non-null and greater than 0.00</li>
     *     <li>Currency must be a non-empty 3-letter ISO 4217 code</li>
     *     <li>Counterparty must be present</li>
     *     <li>Counterparty type must be {@link AccountType#SORT_CODE_ACCOUNT_NUMBER}</li>
     *     <li>Counterparty sort code must be exactly 6 numeric characters</li>
     *     <li>Counterparty account number must be exactly 8 numeric characters</li>
     * </ul>
     *
     * @param payment the Payment object to validate
     * @throws ValidationException if any validation errors are found
     */
    public static void validate(Payment payment) {
        List<Error> errors = new ArrayList<>();

        if (ObjectUtils.isEmpty(payment)) {
            errors.add(new Error().message("Payment object cannot be null"));
            throw new ValidationException(errors);
        }

        // Validate amount > 0.00
        if (ObjectUtils.isEmpty(payment.getAmount()) || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add(new Error().message("Amount must be greater than 0.00"));
        }

        // Validate currency is not empty and ISO code
        if (!StringUtils.hasText(payment.getCurrency()) ||
                !payment.getCurrency().matches("^[A-Z]{3}$")) {
            errors.add(new Error().message("Currency code must be 3 letter ISO 4217 code"));
        }

        // Validate counterparty
        Account counterparty = payment.getCounterparty();
        if (ObjectUtils.isEmpty(counterparty)) {
            errors.add(new Error().message("Counterparty is required"));
        } else {
            // Validate type
            if (ObjectUtils.isEmpty(counterparty.getType())
                    || !AccountType.SORT_CODE_ACCOUNT_NUMBER.getValue().equals(counterparty.getType().toString())) {
                errors.add(new Error().message("Counterparty type must be SORT_CODE_ACCOUNT_NUMBER"));
            }

            // Validate sort code: must be 6 numeric characters
            String sortCode = counterparty.getSortCode();
            if (!StringUtils.hasText(sortCode) || !sortCode.matches("^\\d{6}$")) {
                errors.add(new Error().message("Counterparty sortCode must be 6 numeric characters"));
            }

            // Validate account number: must be 8 numeric characters
            String accountNumber = counterparty.getAccountNumber();
            if (!StringUtils.hasText(accountNumber) || !accountNumber.matches("^\\d{8}$")) {
                errors.add(new Error().message("Counterparty accountNumber must be 8 numeric characters"));
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
