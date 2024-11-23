package com.BancoMalvader.Java_Api.entities.user.employee;

import com.BancoMalvader.Java_Api.entities.operations.Relatory;
import com.BancoMalvader.Java_Api.entities.user.User;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "funcionarios")
@NoArgsConstructor
@Getter
@Setter
public class Employee extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeCode;
    private String job;

    @OneToMany(mappedBy = "employee")
    private final Set<Relatory> relatory = new HashSet<>();

    public Employee(Long id, String name, Instant bornDate, String password, UserType userType, String phone, String CPF, String employeeCode, Long id1, String job) {
        super(id, name, bornDate, password, userType, phone, CPF);
        this.employeeCode = employeeCode;
        this.id = id1;
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

//    public void openAccount(Account account) {
//        //implementação da lógica aqui
//    }
//
//    public void closeAccount(Account account) {
//        //implementação da lógica aqui
//    }
//
//    public Account queryAccountData(int accountNumber) {
//        //implementação da lógica aqui
//        return null;
//    }
//
//    public Client queryClientData(int idClient) {
//        //implementação da lógica aqui
//        return null;
//    }
//
//    public void alterAccoutData(Account account) {
//        //implementação da lógica aqui
//    }
//
//    public void alterClientData(Client client) {
//        //implementação da lógica aqui
//    }
//
//    public void registerEmployee(Employee employee) {
//        //implementação da lógica aqui
//    }
//
//    public void generateMovementReport() {
//        //implementação da lógica aqui
//    }
}
