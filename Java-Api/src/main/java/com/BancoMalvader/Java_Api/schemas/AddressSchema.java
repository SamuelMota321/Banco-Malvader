package com.BancoMalvader.Java_Api.schemas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AddressSchema {
    @NotBlank(message = "Campo não informado")
    private String zipCode;
    @NotBlank(message = "Campo não informado")
    private String local;
    @NotNull(message = "Campo não informado")
    private Integer houseNumber;
    @NotBlank(message = "Campo não informado")
    private String neighborhood;
    @NotBlank(message = "Campo não informado")
    private String city;
    @NotBlank(message = "Campo não informado")
    private String state;
}
