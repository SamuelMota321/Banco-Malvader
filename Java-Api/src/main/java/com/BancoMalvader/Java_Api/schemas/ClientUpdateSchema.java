package com.BancoMalvader.Java_Api.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ClientUpdateSchema {
    private String name;
    @CPF(message = "CPF inválido")
    private String cpf;
    private Instant bornDate;
    private String password;
    private String phone;

}
