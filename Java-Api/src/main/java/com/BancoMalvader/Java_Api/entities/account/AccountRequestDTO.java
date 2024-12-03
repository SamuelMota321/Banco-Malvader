package com.BancoMalvader.Java_Api.entities.account;

import java.time.Instant;

public record AccountRequestDTO(String agency, Double balance, String accountType,
                                Double yieldRate, Double limitt, Instant maturity) {
}
