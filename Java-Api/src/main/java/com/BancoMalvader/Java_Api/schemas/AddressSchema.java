package com.BancoMalvader.Java_Api.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressSchema {

    @JsonProperty("zipCode")
    @NotBlank(message = "O CEP é obrigatório.")
    @Size(max = 20, message = "O CEP deve ter no máximo 20 caracteres.")
    private String zipCode;

    @JsonProperty("local")
    @NotBlank(message = "O local é obrigatório.")
    @Size(max = 100, message = "O local deve ter no máximo 100 caracteres.")
    private String local;

    @JsonProperty("houseNumber")
    @NotNull(message = "O número da casa é obrigatório.")
    private Integer houseNumber;

    @JsonProperty("neighborhood")
    @NotBlank(message = "O bairro é obrigatório.")
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres.")
    private String neighborhood;

    @JsonProperty("city")
    @NotBlank(message = "A cidade é obrigatória.")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres.")
    private String city;

    @JsonProperty("state")
    @NotBlank(message = "O estado é obrigatório.")
    @Size(max = 100, message = "O estado deve ter no máximo 100 caracteres.")
    private String state;
}
