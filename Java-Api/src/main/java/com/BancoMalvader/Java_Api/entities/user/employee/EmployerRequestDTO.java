package com.BancoMalvader.Java_Api.entities.user.employee;


import java.time.Instant;


public record EmployerRequestDTO(String nome, String cpf, String phone, String password, String job, Instant bornDate, String employerCode) {
}
