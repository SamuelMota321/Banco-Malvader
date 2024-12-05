package com.BancoMalvader.Java_Api.exceptions;

public class LimitExcepton extends RuntimeException {
    public LimitExcepton(String message) {
        super(message);
    }

    public LimitExcepton() {
        super("Erro ao setar o limite!");
    }

}
