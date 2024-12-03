package com.BancoMalvader.Java_Api.services;

import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.AddressResquestDTO;
import com.BancoMalvader.Java_Api.entities.user.User;
import com.BancoMalvader.Java_Api.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UsersRepository repository;

    public Address instantiateAddress(AddressResquestDTO dataAddress) {
        Address address = new Address();

        address.setZipCode(dataAddress.zipCode());
        address.setLocal(dataAddress.local());
        address.setHouseNumber(dataAddress.houseNumber());
        address.setCity(dataAddress.city());
        address.setNeighborhood(dataAddress.neighborhood());
        address.setState(dataAddress.state());

        return address;

    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.get();
    }


}