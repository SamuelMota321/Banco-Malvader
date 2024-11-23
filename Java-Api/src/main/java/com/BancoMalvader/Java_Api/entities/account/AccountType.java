package com.BancoMalvader.Java_Api.entities.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
    Conta_corrente(1),
    Conta_Poupanca(2);

    private int code;

    public static AccountType valueOf(int code) {
        for (AccountType value : AccountType.values()) {
            if (value.getCode() == code) return value;
        }
        throw new IllegalArgumentException("Invalid account type code");
    }
}
