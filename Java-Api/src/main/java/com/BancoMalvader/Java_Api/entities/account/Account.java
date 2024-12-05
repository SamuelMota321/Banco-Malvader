package com.BancoMalvader.Java_Api.entities.account;

import com.BancoMalvader.Java_Api.entities.operations.Transation;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.exceptions.InsufficientBalanceException;
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
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;
    protected String agency;
    protected Double balance;
    protected Integer accountType;
    protected Integer accountNumber;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private final Set<Transation> transations = new HashSet<>();

    public Account(Long id, AccountType accountType, Double balance, Integer accountNumber, String agency, Client client) {
        this.id = id;
        setAccountType(accountType);
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.agency = agency;
        this.client = client;
    }

    public AccountType getAccountType() {
        return AccountType.valueOf(accountType);
    }

    public void setAccountType(AccountType accountType) {
        if (accountType != null) this.accountType = accountType.getCode();
    }

    public void debit(Double amount) {
        if (this.balance < amount) {
            throw new InsufficientBalanceException("Dinheiro insuficiente");
        }
        this.balance -= amount;
    }

    public void credit(Double amount) {
        this.balance += amount;
    }


}