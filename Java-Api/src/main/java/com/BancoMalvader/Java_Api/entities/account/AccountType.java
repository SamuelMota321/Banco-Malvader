package com.BancoMalvader.Java_Api.entities.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
    CURRENT("conta_corrente"),
    SAVING("conta_poupanca");
    private String type;
}
