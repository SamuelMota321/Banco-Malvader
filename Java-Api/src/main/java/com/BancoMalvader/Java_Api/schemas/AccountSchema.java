package com.BancoMalvader.Java_Api.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Data
public class AccountSchema {

    @NotBlank(message = "Campo não informado")
    private String accountType;
    @NotNull(message = "Balanca inicial não pode ser nula")
    private Double initialBalance;
    @NotBlank(message = "Campo não informado")
    private String agency;

    private Double limitt;
    private Instant maturity;
    private Double yieldRate;
}
