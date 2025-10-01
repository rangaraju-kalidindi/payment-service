package com.tw.finseta.payment.api;

import com.tw.finseta.payment.model.Payment;
import com.tw.finseta.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit test class for {@link PaymentController}.
 *
 * <p>This class contains unit tests that verify the behavior of the PaymentController
 * REST endpoints. The tests use Mockito framework to mock dependencies and isolate
 * the controller logic for testing.</p>
 *
 * <p>The test class covers the following scenarios:
 * <ul>
 *   <li>GET /payments - Testing retrieval of payments with filters</li>
 *   <li>POST /payments - Testing creation of new payments</li>
 * </ul>
 * </p>
 *
 * @author Ranga Raju
 * @see PaymentController
 * @see PaymentService
 */
@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private Payment samplePayment;

    @BeforeEach
    void setUp() {
        samplePayment = new Payment();
        samplePayment.setCurrency("USD");
        samplePayment.setAmount(BigDecimal.valueOf(100));
    }

    @Test
    void testPaymentsGet_returnsPaymentsList() {
        List<Payment> payments = List.of(samplePayment);
        when(paymentService.getPaymentsByFilters(anyList(), any(BigDecimal.class))).thenReturn(payments);

        ResponseEntity<List<Payment>> response = paymentController.paymentsGet(BigDecimal.valueOf(50), Collections.singletonList("USD"));

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(1, response.getBody().size());
        verify(paymentService, times(1)).getPaymentsByFilters(anyList(), any(BigDecimal.class));
    }

    @Test
    void testPaymentsPost_returnsCreatedPayment() {
        when(paymentService.savePayment(any(Payment.class))).thenReturn(samplePayment);

        ResponseEntity<Payment> response = paymentController.paymentsPost(samplePayment);

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(samplePayment, response.getBody());
        verify(paymentService, times(1)).savePayment(any(Payment.class));
    }
}
