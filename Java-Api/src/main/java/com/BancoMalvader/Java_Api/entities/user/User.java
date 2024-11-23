package com.BancoMalvader.Java_Api.entities.user;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected String CPF;
    protected Instant bornDate;
    protected String phone;
    protected String password;
    protected Integer userType; // Enum para o tipo de usuário (FUNCIONARIO ou CLIENTE)

    @OneToOne
    private Address address;

    public User(Long id, String name, Instant bornDate, String password, UserType userType, String phone, String CPF, Address address) {
        this.id = id;
        this.name = name;
        this.bornDate = bornDate;
        this.password = password;
        setUserType(userType);
        this.phone = phone;
        this.CPF = CPF;
        this.address = address;
    }

    public UserType getUserType() {
        return UserType.valueOf(userType);
    }

    public void setUserType(UserType userType) {
        if (userType != null) this.userType = userType.getCode();
    }

}
