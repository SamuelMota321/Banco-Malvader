package com.BancoMalvader.Java_Api.exceptions;

public class ClientNotFoundException extends RuntimeException {
  public ClientNotFoundException(String message) {
    super(message);
  }

  public ClientNotFoundException() {
    super("Cliente não encontrado!");
  }

}
