package com.BancoMalvader.Java_Api.entities.operations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransationType {
    Deposito(1),
    Saque(2),
    Transferencia(3);

    private int code;

    public static TransationType valueOf(int code) {
        for (TransationType value : TransationType.values()) {
            if (value.getCode() == code) return value;
        }
        throw new IllegalArgumentException("Invalid transation type code");
    }

}
