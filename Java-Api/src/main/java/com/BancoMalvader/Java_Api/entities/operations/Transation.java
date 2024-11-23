package com.BancoMalvader.Java_Api.entities.operations;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "transacao")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TransationType typeTransation;
    private Double transationValue;
    private Instant hourDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_conta")
    private Account account;

    public Transation(Long id, Instant hourDate, Double transationValue, TransationType typeTransation) {
        this.id = id;
        this.hourDate = hourDate;
        this.transationValue = transationValue;
        this.typeTransation = typeTransation;
    }
}
