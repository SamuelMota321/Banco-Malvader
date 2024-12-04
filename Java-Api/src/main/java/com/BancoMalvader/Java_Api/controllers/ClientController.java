package com.BancoMalvader.Java_Api.controllers;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.operations.Transation;
import com.BancoMalvader.Java_Api.entities.user.AddressResquestDTO;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.entities.user.client.ClientRequestDTO;
import com.BancoMalvader.Java_Api.repositories.ClientRepository;
import com.BancoMalvader.Java_Api.schemas.ClientSchema;
import com.BancoMalvader.Java_Api.schemas.TransferencySchema;
import com.BancoMalvader.Java_Api.services.ClientServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/clientes")
public class ClientController {

    @Autowired
    private ClientServices services;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        List<Client> list = services.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{clientId}")
    public ResponseEntity<Client> findById(@PathVariable Long clientId) {
        Client obj = services.findById(clientId);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/query-extract/{clientId}")
    public ResponseEntity<?> getExtract(@PathVariable Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao e"));

        Set<Transation> transations = services.queryExtract(client);

        if (transations == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Essa conta ainda não possui nenhuma transação"));

        return ResponseEntity.status(HttpStatus.FOUND).body(transations);

    }

    @GetMapping("/query-limit/{clientId}")
    public ResponseEntity<?> getLimit(@PathVariable Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));

        Double limit = services.queryLimit(client);

        return ResponseEntity.status(HttpStatus.FOUND).body(Map.of("Limit:", limit));
    }


    @GetMapping("/query-balance/{clientId}")
    public ResponseEntity<String> queryBalance(@PathVariable Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        Double balance = services.getBalance(client);

        return ResponseEntity.status(HttpStatus.FOUND).body("O seu saldo atual é igual a: " + balance);
    }

    @PostMapping
    public ResponseEntity<Client> registerClient(@Valid @RequestBody ClientSchema schema) {
        AddressResquestDTO addressResquestDTO = new AddressResquestDTO(schema.getAddress().getZipCode(), schema.getAddress().getLocal(), schema.getAddress().getHouseNumber(), schema.getAddress().getNeighborhood(), schema.getAddress().getCity(), schema.getAddress().getState());
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO(schema.getName(), schema.getCpf(), schema.getPhone(), schema.getPassword(), schema.getBornDate());

        Client savedClient = services.registerClient(clientRequestDTO, addressResquestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @PostMapping("/deposit/{clientId}")
    public ResponseEntity<String> deposit(@PathVariable Long clientId, @RequestBody Double value) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Account account = services.deposit(client, value);
        return ResponseEntity.status(HttpStatus.OK).body("O seu saldo agora é igual a: " + account.getBalance());
    }

    @PostMapping("/withdraw/{clientId}")
    public ResponseEntity<String> withdraw(@PathVariable Long clientId, @RequestBody Double value) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try{
            Account account = services.withdraw(client, value);
            return ResponseEntity.status(HttpStatus.OK).body("O seu saldo agora é igual a: " + account.getBalance());

        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O valor na conta não é suficiente para efetuar o saque");
        }
    }

    @PostMapping("/transferency/{clientId}")
    public ResponseEntity<?> transferency(@PathVariable Long clientId, @RequestBody TransferencySchema schema) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        services.transferency(client, schema);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("Transferencia: ", "A transferencia de " + schema.getValue() + " para a conta " + schema.getAccountNumber() + " foi efetuada com sucesso"));

    }
}


