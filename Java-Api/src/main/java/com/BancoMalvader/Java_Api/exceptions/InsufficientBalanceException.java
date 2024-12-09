package com.BancoMalvader.Java_Api.exceptions;

public class InsufficientBalanceException extends RuntimeException {
  public InsufficientBalanceException() {
        super("Saldo insuficiente para a transação");
    }

  public InsufficientBalanceException(String message) {
    super(message);
  }
}
