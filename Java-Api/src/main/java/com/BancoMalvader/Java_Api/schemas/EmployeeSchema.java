package com.BancoMalvader.Java_Api.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EmployeeSchema {

    @JsonProperty("name")
    @NotBlank(message = "O nome não pode estar vazio.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String name;

    @JsonProperty("cpf")
    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 6, max = 11, message = "O CPF deve conter entre 6 e 11 dígitos.")
    private String cpf;

    @JsonProperty("phone")
    @NotBlank(message = "O telefone é obrigatório.")
    @Size(min = 4, max = 11, message = "O telefone deve conter entre 4 e 11 dígitos.")
    private String phone;

    @JsonProperty("password")
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String password;

    @JsonProperty("employeeCode")
    @NotBlank(message = "O código de funcionário é obrigatório.")
    private String employeeCode;

    @JsonProperty("job")
    @NotBlank(message = "O cargo é obrigatório.")
    private String job;

    @JsonProperty("bornDate")
    @NotNull(message = "A data de nascimento é obrigatória.")
    private Instant bornDate;

    @JsonProperty("address")
    @NotNull(message = "O endereço é obrigatório.")
    private AddressSchema address;
}
