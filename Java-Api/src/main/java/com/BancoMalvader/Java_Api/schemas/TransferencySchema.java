package com.BancoMalvader.Java_Api.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferencySchema {
    @JsonProperty("accountNumber")
    @NotBlank
    private Integer accountNumber;

    @JsonProperty("value")
    @NotNull
    private Double value;

}
