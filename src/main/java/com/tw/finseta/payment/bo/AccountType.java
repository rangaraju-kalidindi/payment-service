package com.tw.finseta.payment.bo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Enumeration for different types of accounts that can be linked to a payment.
 *
 * <p>Defines possible account type values and descriptions for counterparty classification.</p>
 *
 * @author Ranga Raju
 */
@Getter
public enum AccountType {
    SORT_CODE_ACCOUNT_NUMBER("SORT_CODE_ACCOUNT_NUMBER");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static AccountType fromValue(String value) {
        for (AccountType b : AccountType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}