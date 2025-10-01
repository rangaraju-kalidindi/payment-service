package com.tw.finseta.payment.service;

import com.tw.finseta.payment.bo.AccountBo;
import com.tw.finseta.payment.bo.PaymentBo;
import com.tw.finseta.payment.model.Payment;
import com.tw.finseta.payment.repository.AccountRepository;
import com.tw.finseta.payment.repository.PaymentRepository;
import com.tw.finseta.payment.util.PaymentUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for handling payment operations.
 * This class provides methods to save payments and retrieve payments based on filters.
 * It performs validation on payments before saving and prevents duplicate account entries.
 *
 * @author Ranga Raju.
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);

    private PaymentRepository paymentRepository;
    private AccountRepository accountRepository;

    /**
     * Constructs a PaymentServiceImpl with the required repositories.
     *
     * @param paymentRepository the payment repository to be used
     * @param accountRepository the account repository to be used
     */
    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Saves a payment after validation and checks for existing counterparty accounts.
     * If an account with the given account number and sort code exists, the existing account is used to avoid duplicates.
     *
     * @param payment the Payment object to be saved
     * @return the saved Payment object with any updates applied
     * @throws IllegalArgumentException if the payment validation fails
     */
    @Override
    public Payment savePayment(Payment payment) {
        PaymentBo paymentBo = PaymentUtil.paymentToPaymentBo.apply(payment);

        // Proceed with saving paymentBo if account already exists fetch existing one - accountNumber, sortCode unique
        if (paymentBo.getCounterparty() != null) {
            AccountBo incomingAccount = paymentBo.getCounterparty();
            // Lookup existing account by accountNumber and sortCode
            Optional<AccountBo> existingAccountOpt = accountRepository.findByAccountNumberAndSortCode(
                    incomingAccount.getAccountNumber(), incomingAccount.getSortCode());
            // Use existing account to avoid duplicates
            existingAccountOpt.ifPresent(paymentBo::setCounterparty);
        }

        logger.info("Saving paymentBo: {}", paymentBo);
        return PaymentUtil.paymentBoToPayment.apply(paymentRepository.save(paymentBo));
    }

    /**
     * Retrieves a list of payments filtered by currencies and minimum amount.
     * If the currency list is empty or null, no filtering by currency is applied.
     *
     * @param currencies the list of currency codes to filter by, can be null or empty
     * @param minAmount the minimum payment amount to filter payments
     * @return a list of payments matching the filter criteria
     */
    @Override
    public List<Payment> getPaymentsByFilters(List<String> currencies, BigDecimal minAmount) {
        // If currencies list is empty, pass null to query for no filtering on currencies
        List<PaymentBo> liPaymentBo = paymentRepository.findByCurrencyInAndAmountGreaterThanEqual(
                (currencies == null || currencies.isEmpty()) ? null : currencies,
                minAmount);
        return PaymentUtil.paymentBoListToPaymentList(liPaymentBo);
    }
}
