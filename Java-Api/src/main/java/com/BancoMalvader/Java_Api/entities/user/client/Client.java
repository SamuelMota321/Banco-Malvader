package com.BancoMalvader.Java_Api.entities.user.client;

import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.User;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Client extends User {

    private int id;

    public Client(String name, String CPF, LocalDate bornDate, String phone, String password, UserType userType, Address address) {
        super(name, CPF, bornDate, phone, password, userType, address);
    }

    @Override
    public boolean login(String password) {
        return false;
    }


    @Override
    public void logout() {

    }

    @Override
    public void register() {

    }

    public double queryBalance() {
        //implementação da lógica aqui
        return 0;
    }

    public void deposit(double value) {
        //implementação da lógica aqui
    }

    public boolean withdraw(double value) {
        //implementação da lógica aqui
        return false;
    }

    public String queryExtract() {
        //implementação da lógica aqui
        return "";
    }

    public double queryLimit() {
        //implementação da lógica aqui
        return 0;
    }
}
