package com.tw.finseta.payment.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Represents a payment account entity identified by account number and sort code.
 * Used as a counterparty for payments.
 * This class is annotated for JPA and is persisted in the "accounts" table,
 * with unique constraints on accountNumber and sortCode fields.
 * Typical usage includes associating this entity as a counterparty in payment operations.
 * author: Ranga Raju
 */
@Entity
@Table(name = "accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"accountNumber", "sortCode"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccountBo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private AccountType type;

    @EqualsAndHashCode.Include
    private String accountNumber;

    @EqualsAndHashCode.Include
    private String sortCode;

    @JsonProperty("type")
    public AccountType getType() {
        return type;
    }

    @JsonProperty("accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    @JsonProperty("sortCode")
    public String getSortCode() {
        return sortCode;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", type=" + type +
                ", accountNumber='********'" +
                ", sortCode='" + sortCode + '\'' +
                '}';
    }
}