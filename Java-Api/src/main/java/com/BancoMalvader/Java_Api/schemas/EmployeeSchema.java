package com.BancoMalvader.Java_Api.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.Instant;

@Getter
@Setter
@Data
public class EmployeeSchema {

    @NotBlank(message = "Campo não informado")
    private String name;
    @CPF(message = "CPF inválido")
    @NotBlank(message = "Campo não informado")
    private String cpf;
    @NotBlank(message = "Campo não informado")
    private String phone;
    @NotBlank(message = "Campo não informado")
    private String password;
    @NotBlank(message = "Campo não informado")
    private String employeeCode;
    @NotBlank(message = "Campo não informado")
    private String job;
    @NotNull(message = "Campo não informado")
    private Instant bornDate;
    @NotNull(message = "Campo não informado")
    private AddressSchema address;
}
