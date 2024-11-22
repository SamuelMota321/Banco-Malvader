package com.BancoMalvader.Java_Api.entities.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Address {
    private String zipCode;
    private String local;
    private int houseNumber;
    private String neighborhood;
    private String city;
    private String state;

    public String toString() {
        return local + ", " + houseNumber + ", " + neighborhood + ", " + city + ", " + state + ", CEP: " + zipCode;
    }
}
