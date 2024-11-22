package com.BancoMalvader.Java_Api.entities.user.employee;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.User;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Employee extends User {

    private int id;
    private String employeeCode;
    private String job;

    public Employee(String name, String CPF, LocalDate bornDate, String phone, String password, UserType userType, Address address, String employeeCode, String job) {
        super(name, CPF, bornDate, phone, password, userType, address);
        this.employeeCode = employeeCode;
        this.job = job;
    }

    @Override
    public boolean login(String password) {
        //Implemente a lógica aq
        return false;
    }

    @Override
    public void logout() {
        //Implemente a lógica aq
    }

    @Override
    public void register() {
        //Implemente a lógica aq
    }

    public void openAccount(Account account) {
        //implementação da lógica aqui
    }

    public void closeAccount(Account account) {
        //implementação da lógica aqui
    }

    public Account queryAccountData(int accountNumber) {
        //implementação da lógica aqui
        return null;
    }

    public Client queryClientData(int idClient) {
        //implementação da lógica aqui
        return null;
    }

    public void alterAccoutData(Account account) {
        //implementação da lógica aqui
    }

    public void alterClientData(Client client) {
        //implementação da lógica aqui
    }

    public void registerEmployee(Employee employee) {
        //implementação da lógica aqui
    }

    public void generateMovementReport() {
        //implementação da lógica aqui
    }
}
