package com.tw.finseta.payment.util;

import com.tw.finseta.payment.dao.AccountDAO;
import com.tw.finseta.payment.dao.AccountType;
import com.tw.finseta.payment.dao.PaymentDAO;
import com.tw.finseta.payment.model.Account;
import com.tw.finseta.payment.model.Payment;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class for converting between API model classes and business entity classes
 * related to Payment and Account.
 *
 * This class provides reusable, null-safe Java 8 {@link java.util.function.Function} instances
 * to convert back and forth between:
 * <ul>
 *     <li>{@link Account} (API model) and {@link AccountDAO} (business entity)</li>
 *     <li>{@link Payment} (API model) and {@link PaymentDAO} (business entity)</li>
 *     <li>Also supports converting lists of PaymentDAO to Payment</li>
 * </ul>
 *
 * Conversion functions use Spring's {@link ObjectUtils#isEmpty(Object)} for null and emptiness checks.
 *
 * Typical use cases include mapping between REST API layer and persistence/business layer.
 *
 * Example usage:
 * <pre>
 *     AccountDAO entity = PaymentUtil.accountToAccountDAO.apply(apiAccount);
 *     Payment apiModel = PaymentUtil.paymentDAOToPayment.apply(paymentEntity);
 *     List&lt;Payment&gt; apiList = PaymentUtil.paymentDAOListToPaymentList(paymentDAOList);
 * </pre>
 *
 * Author: Ranga Raju
 */
public class PaymentUtil {

    /**
     * Function to convert {@link Account} API model to {@link AccountDAO} entity.
     */
    public static final Function<Account, AccountDAO> accountToAccountDAO = account -> {
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        AccountDAO accountDAO = new AccountDAO();
        if (!ObjectUtils.isEmpty(account.getType())) {
            accountDAO.setType(AccountType.valueOf(account.getType().getValue()));
        } else {
            accountDAO.setType(null);
        }
        accountDAO.setAccountNumber(account.getAccountNumber());
        accountDAO.setSortCode(account.getSortCode());
        return accountDAO;
    };

    /**
     * Function to convert {@link AccountDAO} entity to {@link Account} API model.
     */
    public static final Function<AccountDAO, Account> accountDAOToAccount = accountDAO -> {
        if (ObjectUtils.isEmpty(accountDAO)) {
            return null;
        }
        Account account = new Account();
        if (!ObjectUtils.isEmpty(accountDAO.getType())) {
            account.setType(Account.TypeEnum.fromValue(accountDAO.getType().name()));
        } else {
            account.setType(null);
        }
        account.setAccountNumber(accountDAO.getAccountNumber());
        account.setSortCode(accountDAO.getSortCode());
        return account;
    };

    /**
     * Function to convert {@link Payment} API model to {@link PaymentDAO} entity.
     */
    public static final Function<Payment, PaymentDAO> paymentToPaymentDAO = payment -> {
        if (ObjectUtils.isEmpty(payment)) {
            return null;
        }
        PaymentDAO paymentDAO = new PaymentDAO();
        paymentDAO.setCurrency(payment.getCurrency());
        paymentDAO.setAmount(payment.getAmount());
        paymentDAO.setCounterparty(accountToAccountDAO.apply(payment.getCounterparty()));
        return paymentDAO;
    };

    /**
     * Function to convert {@link PaymentDAO} entity to {@link Payment} API model.
     */
    public static final Function<PaymentDAO, Payment> paymentDAOToPayment = paymentDAO -> {
        if (ObjectUtils.isEmpty(paymentDAO)) {
            return null;
        }
        Payment payment = new Payment();
        payment.setCurrency(paymentDAO.getCurrency());
        payment.setAmount(paymentDAO.getAmount());
        payment.setCounterparty(accountDAOToAccount.apply(paymentDAO.getCounterparty()));
        return payment;
    };

    /**
     * Converts a list of {@link PaymentDAO} entities to a list of {@link Payment} API models.
     *
     * @param paymentDAOList the list of PaymentDAO entities
     * @return the corresponding list of Payment API models; empty list if input is empty or null
     */
    public static List<Payment> paymentDAOListToPaymentList(List<PaymentDAO> paymentDAOList) {
        if (paymentDAOList == null || paymentDAOList.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return paymentDAOList.stream()
                .map(paymentDAOToPayment)
                .collect(Collectors.toList());
    }
}