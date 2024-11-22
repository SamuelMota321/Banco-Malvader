package com.BancoMalvader.Java_Api.entities.user.client;

import com.BancoMalvader.Java_Api.entities.user.User;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "clientes")
@Setter
@Getter
@NoArgsConstructor
public class Client extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Client(Long id, String name, Instant bornDate, String password, UserType userType, String phone, String CPF, Long id1) {
        super(id, name, bornDate, password, userType, phone, CPF);
        this.id = id1;
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
