package com.tw.finseta.payment.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Entity representing a monetary payment.
 * Contains core details such as the currency, amount, and associated counterparty account.
 * This entity is persisted in the "payments" table.
 * Provides a JPA mapping to associate an Account as the counterparty for the payment.
 * Typical usage includes representing a payment in financial transactions within the application.
 *
 * Author: Ranga Raju
 */
@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PaymentBo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "accounts_id")
    @JsonProperty("counterparty")
    private AccountBo counterparty;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private String currency;

    public PaymentBo(String currency, BigDecimal amount, AccountBo counterparty) {
        this.currency = currency;
        this.amount = amount;
        this.counterparty = counterparty;
    }
}