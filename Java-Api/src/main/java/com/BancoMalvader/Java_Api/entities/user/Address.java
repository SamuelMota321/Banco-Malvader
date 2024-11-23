package com.BancoMalvader.Java_Api.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zipCode;
    private String local;
    private int houseNumber;
    private String neighborhood;
    private String city;
    private String state;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // a unidade dependente leva a coluna no construtor

    public Address(Long id, String zipCode, int houseNumber, String neighborhood, String state, User user, String city, String local) {
        this.id = id;
        this.zipCode = zipCode;
        this.houseNumber = houseNumber;
        this.neighborhood = neighborhood;
        this.state = state;
        this.user = user;
        this.city = city;
        this.local = local;
    }

    public String toString() {
        return local + ", " + houseNumber + ", " + neighborhood + ", " + city + ", " + state + ", CEP: " + zipCode;
    }
}
