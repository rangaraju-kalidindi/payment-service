package com.tw.finseta.payment.util;

import com.tw.finseta.payment.bo.AccountBo;
import com.tw.finseta.payment.bo.AccountType;
import com.tw.finseta.payment.bo.PaymentBo;
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
 *     <li>{@link Account} (API model) and {@link AccountBo} (business entity)</li>
 *     <li>{@link Payment} (API model) and {@link PaymentBo} (business entity)</li>
 *     <li>Also supports converting lists of PaymentBo to Payment</li>
 * </ul>
 *
 * Conversion functions use Spring's {@link ObjectUtils#isEmpty(Object)} for null and emptiness checks.
 *
 * Typical use cases include mapping between REST API layer and persistence/business layer.
 *
 * Example usage:
 * <pre>
 *     AccountBo entity = PaymentUtil.accountToAccountBo.apply(apiAccount);
 *     Payment apiModel = PaymentUtil.paymentBoToPayment.apply(paymentEntity);
 *     List&lt;Payment&gt; apiList = PaymentUtil.paymentBoListToPaymentList(paymentBoList);
 * </pre>
 *
 * Author: Ranga Raju
 */
public class PaymentUtil {

    /**
     * Function to convert {@link Account} API model to {@link AccountBo} entity.
     */
    public static final Function<Account, AccountBo> accountToAccountBo = account -> {
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        AccountBo accountBo = new AccountBo();
        if (!ObjectUtils.isEmpty(account.getType())) {
            accountBo.setType(AccountType.valueOf(account.getType().getValue()));
        } else {
            accountBo.setType(null);
        }
        accountBo.setAccountNumber(account.getAccountNumber());
        accountBo.setSortCode(account.getSortCode());
        return accountBo;
    };

    /**
     * Function to convert {@link AccountBo} entity to {@link Account} API model.
     */
    public static final Function<AccountBo, Account> accountBoToAccount = accountBo -> {
        if (ObjectUtils.isEmpty(accountBo)) {
            return null;
        }
        Account account = new Account();
        if (!ObjectUtils.isEmpty(accountBo.getType())) {
            account.setType(Account.TypeEnum.fromValue(accountBo.getType().name()));
        } else {
            account.setType(null);
        }
        account.setAccountNumber(accountBo.getAccountNumber());
        account.setSortCode(accountBo.getSortCode());
        return account;
    };

    /**
     * Function to convert {@link Payment} API model to {@link PaymentBo} entity.
     */
    public static final Function<Payment, PaymentBo> paymentToPaymentBo = payment -> {
        if (ObjectUtils.isEmpty(payment)) {
            return null;
        }
        PaymentBo paymentBo = new PaymentBo();
        paymentBo.setCurrency(payment.getCurrency());
        paymentBo.setAmount(payment.getAmount());
        paymentBo.setCounterparty(accountToAccountBo.apply(payment.getCounterparty()));
        return paymentBo;
    };

    /**
     * Function to convert {@link PaymentBo} entity to {@link Payment} API model.
     */
    public static final Function<PaymentBo, Payment> paymentBoToPayment = paymentBo -> {
        if (ObjectUtils.isEmpty(paymentBo)) {
            return null;
        }
        Payment payment = new Payment();
        payment.setCurrency(paymentBo.getCurrency());
        payment.setAmount(paymentBo.getAmount());
        payment.setCounterparty(accountBoToAccount.apply(paymentBo.getCounterparty()));
        return payment;
    };

    /**
     * Converts a list of {@link PaymentBo} entities to a list of {@link Payment} API models.
     *
     * @param paymentBoList the list of PaymentBo entities
     * @return the corresponding list of Payment API models; empty list if input is empty or null
     */
    public static List<Payment> paymentBoListToPaymentList(List<PaymentBo> paymentBoList) {
        if (paymentBoList == null || paymentBoList.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return paymentBoList.stream()
                .map(paymentBoToPayment)
                .collect(Collectors.toList());
    }
}