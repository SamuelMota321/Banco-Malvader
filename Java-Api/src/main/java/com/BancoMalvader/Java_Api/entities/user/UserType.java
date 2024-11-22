package com.BancoMalvader.Java_Api.entities.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
    ADMIN("funcionario"),
    USER("cliente");
    private String type;

}
