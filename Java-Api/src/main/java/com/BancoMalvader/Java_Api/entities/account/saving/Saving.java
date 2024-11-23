package com.BancoMalvader.Java_Api.entities.account.saving;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.AccountType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "conta_poupanca")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Saving extends Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double yieldRate;


    public Saving(Long id, AccountType accountType, Double balance, Integer accountNumber, String agency, Client client, Long id1, Double yieldRate) {
        super(id, accountType, balance, accountNumber, agency, client);
        this.id = id1;
        this.yieldRate = yieldRate;
    }

    public double calculateYield() {
        //implementação da lógica aqui
        return 0;
    }

}
