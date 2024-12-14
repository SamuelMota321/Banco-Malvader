package com.BancoMalvader.Java_Api.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TransferencySchema {
    @NotBlank(message = "Campo não informado")
    private Integer accountNumber;
    @NotBlank(message = "Campo não informado")
    private Double value;
}
