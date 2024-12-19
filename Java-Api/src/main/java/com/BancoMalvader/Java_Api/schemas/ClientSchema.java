package com.BancoMalvader.Java_Api.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.Instant;

@Getter
@Setter
@Data
public class ClientSchema {
    @NotBlank(message = "Campo não informado")
    private String name;
    @CPF(message = "CPF inválido")
    @NotBlank
    private String cpf;
    @NotNull(message = "Campo não informado")
    private Instant bornDate;
    @NotBlank(message = "Campo não informado")
    private String password;
    @NotBlank(message = "Campo não informado")
    private String phone;
    @Valid
    private AddressSchema address;
}
