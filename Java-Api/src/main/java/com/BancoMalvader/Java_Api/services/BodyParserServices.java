package com.BancoMalvader.Java_Api.services;

import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.BancoMalvader.Java_Api.repositories.AddressRepository;
import com.BancoMalvader.Java_Api.repositories.ClientRepository;
import com.BancoMalvader.Java_Api.schemas.ClientSchema;
import com.BancoMalvader.Java_Api.schemas.EmployeeSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BodyParserServices {


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Employee convertToEntity(EmployeeSchema schema) {
        Address address = new Address(
                null,
                schema.getAddress().getZipCode(),
                schema.getAddress().getLocal(),
                schema.getAddress().getHouseNumber(),
                schema.getAddress().getNeighborhood(),
                schema.getAddress().getCity(),
                schema.getAddress().getState()
        );
        addressRepository.save(address);

        return new Employee(
                null,
                schema.getName(),
                schema.getBornDate(),
                schema.getPassword(),
                null, // UserType será atribuído no Service
                schema.getPhone(),
                schema.getCpf(),
                schema.getEmployeeCode(),
                schema.getJob(),
                address
        );
    }

    // Metodo utilitário para converter o schema em entidade Client
    public Client convertToEntity(ClientSchema schema) {
        Address address = new Address(
                null,
                schema.getAddress().getZipCode(),
                schema.getAddress().getLocal(),
                schema.getAddress().getHouseNumber(),
                schema.getAddress().getNeighborhood(),
                schema.getAddress().getCity(),
                schema.getAddress().getState()
        );
        addressRepository.save(address);

        return new Client(
                null,
                schema.getName(),
                schema.getCpf(),
                schema.getPhone(),
                schema.getPassword(),
                null, // UserType será atribuído no Service
                schema.getBornDate(),
                address
        );
    }

}
