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
@AllArgsConstructor
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
    @MapsId
    private User user;

    public String toString() {
        return local + ", " + houseNumber + ", " + neighborhood + ", " + city + ", " + state + ", CEP: " + zipCode;
    }
}
