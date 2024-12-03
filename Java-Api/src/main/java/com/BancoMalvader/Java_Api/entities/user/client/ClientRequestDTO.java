package com.BancoMalvader.Java_Api.entities.user.client;

import java.time.Instant;

public record ClientRequestDTO(String name, String CPF, String phone, String password, Instant bornDate) {
}
