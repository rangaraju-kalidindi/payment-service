package com.tw.finseta.payment.service;

import com.tw.finseta.payment.model.Payment;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service abstraction (interface) for payment business operations.
 *
 * <p>Declares methods for saving and querying payments, to be implemented by service classes.</p>
 *
 * @author Ranga Raju.
 */
public interface PaymentService {
    Payment savePayment(Payment payment);
    List<Payment> getPaymentsByFilters(List<String> currencies, BigDecimal minAmount);
}
