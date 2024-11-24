package com.BancoMalvader.Java_Api.entities.account.current;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.AccountType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "conta_corrente")
@Getter
@Setter
@NoArgsConstructor
public class Current extends Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Double limitt;
    private Instant maturity;


    public Current(Long id, AccountType accountType, Double balance, Integer accountNumber, String agency, Client client, Double limitt, Instant maturity) {
        super(id, accountType, balance, accountNumber, agency, client);
        this.limitt = limitt;
        this.maturity = maturity;
    }

    public double queryLimit() {
        // implementação da lógica aqui
        return 0;
    }
}
