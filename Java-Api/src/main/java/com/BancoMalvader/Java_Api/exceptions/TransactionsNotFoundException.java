package com.BancoMalvader.Java_Api.exceptions;

public class TransactionsNotFoundException extends RuntimeException {
    public TransactionsNotFoundException(String message) {
        super(message);
    }

  public TransactionsNotFoundException() {
    super("Usuário não possui transações salva no sistema.");
  }

}
