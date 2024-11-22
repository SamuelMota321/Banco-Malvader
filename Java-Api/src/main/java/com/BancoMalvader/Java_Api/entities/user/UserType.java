package com.BancoMalvader.Java_Api.entities.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
    Funcionario(1),
    Cliente(2);

    private int code;

    public static UserType valueOf(int code){
        for (UserType value : UserType.values()){
            if(value.getCode() == code) return value;
        }
        throw new IllegalArgumentException("Invalid user type code");
    }
}
