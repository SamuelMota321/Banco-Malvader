package com.BancoMalvader.Java_Api.entities.user.client;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.User;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "clientes")
@NoArgsConstructor
@Getter
@Setter
public class Client extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Account account; // a conta depende do cliente, por isso não deve aparecer no construtor

    public Client(Long id, String name, String CPF, String phone, String password, UserType userType, Instant bornDate, Address address) {
        super(id, name, bornDate, password, userType, phone, CPF, address);
    }

    public Client(ClientRequestDTO data, Address address, UserType userType){
        super(null, data.name(), data.bornDate(), data.password(), userType, data.phone(), data.CPF(), address);
    }


}
