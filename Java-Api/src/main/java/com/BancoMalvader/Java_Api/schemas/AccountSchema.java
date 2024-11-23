package com.BancoMalvader.Java_Api.schemas;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AccountSchema {
    private String accountType;
    private Double initialBalance;
    private String agency;
    private Double limitt;
    private Instant maturity;
    private Double yieldRate;
}
