package com.BancoMalvader.Java_Api.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ClientUpdateSchema {

    @JsonProperty("name")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String name;

    @JsonProperty("cpf")
    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 6, max = 11, message = "O CPF deve conter entre 6 e 11 dígitos.")
    private String cpf;

    @JsonProperty("bornDate")
    @NotNull(message = "A data de nascimento é obrigatória.")
    private Instant bornDate;

    @JsonProperty("password")
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres.")
    private String password;

    @JsonProperty("phone")
    @NotBlank(message = "O telefone é obrigatório.")
    @Size(max = 15, message = "O telefone deve ter no máximo 15 caracteres.")
    private String phone;

}
