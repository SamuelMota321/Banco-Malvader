package com.BancoMalvader.Java_Api.entities.account;

import com.BancoMalvader.Java_Api.entities.operations.Transation;
import com.BancoMalvader.Java_Api.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "conta")
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String agency;
    protected Double balance;
    protected Integer accountType;
    protected Integer accountNumber;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private User user;

    @OneToMany(mappedBy = "account")
    private final Set<Transation> transations = new HashSet<>();

    public Account(Long id, AccountType accountType, Double balance, Integer accountNumber, String agency) {
        this.id = id;
        setAccountType(accountType);
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.agency = agency;
    }

    public void setAccountType(AccountType accountType) {
        if (accountType != null) this.accountType = accountType.getCode();
    }

    public void deposit(double valor) {
        // implementação da lógica aqui
    }

    public boolean withdraw(double valor) {
        // implementação da lógica aqui
        return false;
    }

    public double queryBalance() {
        // implementação da lógica aqui
        return 0;
    }
}