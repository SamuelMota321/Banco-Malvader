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
import java.time.LocalDate;

@Table(name = "conta_corrente")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Current extends Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double limitt;
    private Instant maturity;

    public Current(Long id, AccountType accountType, Double balance, Integer accountNumber, String agency, Client client, Long id1, Double limitt, Instant maturity) {
        super(id, accountType, balance, accountNumber, agency, client);
        this.id = id1;
        this.limitt = limitt;
        this.maturity = maturity;
    }

    public double queryLimit() {
        // implementação da lógica aqui
        return 0;
    }
}
