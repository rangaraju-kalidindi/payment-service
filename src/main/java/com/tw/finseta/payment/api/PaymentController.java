package com.tw.finseta.payment.api;

import com.tw.finseta.payment.model.Payment;
import com.tw.finseta.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for managing payments.
 * <p>
 * Provides endpoints to retrieve a list of payments with optional filtering by minimum amount and currencies,
 * and to create new payments.
 * </p>
 * <p>
 * This controller delegates business logic to {@link PaymentService}.
 * </p>
 *
 * @author Ranga Raju
 */
@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentsApi {

    private static final Logger logger = LogManager.getLogger(PaymentController.class);
    private PaymentService paymentService;

    /**
     * Constructor with PaymentService injection.
     *
     * @param paymentService Service for payment operations
     */
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Retrieve a list of payments optionally filtered by minimum amount and currencies.
     *
     * @param minAmount Optional filter to select payments with amount >= minAmount
     * @param currencies Optional filter to select payments matching specified currency codes
     * @return ResponseEntity containing list of matching payments and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<List<Payment>> paymentsGet(BigDecimal minAmount, List<String> currencies) {
        logger.info("Received getPaymentsByFilters request: currencies: {} + minAmount: {}", currencies, minAmount);
        List<Payment> payments = paymentService.getPaymentsByFilters(currencies, minAmount);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    /**
     * Create a new payment.
     *
     * @param payment Payment object to create; must be valid
     * @return ResponseEntity containing the created payment object and HTTP status 201 (Created)
     */
    @Override
    public ResponseEntity<Payment> paymentsPost(Payment payment) {
        logger.info("Received createPayment request: {}", payment);
        Payment savedPayment = paymentService.savePayment(payment);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }
}

