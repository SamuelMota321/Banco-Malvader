package com.BancoMalvader.Java_Api.entities.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public abstract class User {

    protected int id;

    @Setter
    protected String name;

    @Setter
    protected String CPF;

    @Setter
    protected LocalDate bornDate;

    @Setter
    protected String phone;

    @Setter
    protected String password;

    @Setter
    protected UserType userType; // Enum para o tipo de usuário (FUNCIONARIO ou CLIENTE)

    @Setter
    protected Address address;

    public User(String name, String CPF, LocalDate bornDate, String phone, String password, UserType userType, Address address) {
        this.name = name;
        this.CPF = CPF;
        this.bornDate = bornDate;
        this.phone = phone;
        this.password = password;
        this.userType = userType;
        this.address = address;
    }

    public abstract boolean login(String password);

    public abstract void logout();

    public abstract void register();

    public String dataQuery() {
        return "ID: " + id + ", Nome: " + name + ", CPF: " + CPF +
                ", Data de Nascimento: " + bornDate + ", Telefone: " + phone;
    }
}
