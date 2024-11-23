package com.BancoMalvader.Java_Api.controllers;

import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.repositories.AddressRepository;
import com.BancoMalvader.Java_Api.schemas.AddressSchema;
import com.BancoMalvader.Java_Api.schemas.ClientSchema;
import com.BancoMalvader.Java_Api.services.ClientServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClientController {

    @Autowired
    private ClientServices services;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        List<Client> list = services.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) {
        Client obj = services.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Client> registerClient(@Valid @RequestBody ClientSchema schema) {
        // Converter o schema para entidade Client
        Client client = convertToEntity(schema);

        Client savedClient = services.registerClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    // Metodo utilitário para converter o schema em entidade Client
    private Client convertToEntity(ClientSchema schema) {
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

