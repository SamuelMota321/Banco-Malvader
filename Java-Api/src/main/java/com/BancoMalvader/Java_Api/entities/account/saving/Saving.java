package com.BancoMalvader.Java_Api.entities.account.saving;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.AccountType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "conta_poupanca")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Saving extends Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Double yieldRate;


    public Saving(Long id, AccountType accountType, Double balance, Integer accountNumber, String agency, Client client, Double yieldRate) {
        super(id, accountType, balance, accountNumber, agency, client);
        this.yieldRate = yieldRate;
    }
}
