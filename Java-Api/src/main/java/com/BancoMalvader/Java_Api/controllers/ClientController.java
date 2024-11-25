package com.BancoMalvader.Java_Api.controllers;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.operations.Transation;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.repositories.AddressRepository;
import com.BancoMalvader.Java_Api.repositories.ClientRepository;
import com.BancoMalvader.Java_Api.schemas.ClientSchema;
import com.BancoMalvader.Java_Api.services.BodyParserServices;
import com.BancoMalvader.Java_Api.services.ClientServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private AddressRepository addressRepository;

    @Autowired
    private BodyParserServices bodyParserServices;
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

    @PostMapping
    public ResponseEntity<Client> registerClient(@Valid @RequestBody ClientSchema schema) {
        Client client = bodyParserServices.convertToEntity(schema);
        Client savedClient = services.registerClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @GetMapping("/query-balance/{clientId}")
    public ResponseEntity<String> queryBalance(@PathVariable Long clientId) {
        Double balance = services.queryBalance(clientId);
        String message = "O seu saldo atual é igual a: " + balance;
        if (balance != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(message);
        } else {
            message = "Conta não encontrada";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }


    @PostMapping("/deposit/{clientId}")
    public ResponseEntity<String> deposit(@PathVariable Long clientId, @RequestBody Double value) {
        Account account = services.deposit(clientId, value);
        String message = "";
        if (account != null) {
            message = "O seu saldo agora é igual a: " + account.getBalance();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            Optional<Client> optionalClient = clientRepository.findById(clientId);
            if (optionalClient.isPresent()) {
                message = "Conta não encontrada";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            } else {
                message = "Cliente não encontrado";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }
        }

    }

    @PostMapping("/withdraw/{clientId}")
    public ResponseEntity<String> withdraw(@PathVariable Long clientId, @RequestBody Double value) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        String message;
        if (optionalClient.isPresent()) {
            Account account = optionalClient.get().getAccount();
            if (account != null) {
                if (value < account.getBalance()) {
                    account = services.withdraw(clientId, value);
                    message = "O seu saldo agora é igual a: " + account.getBalance();
                    return ResponseEntity.status(HttpStatus.OK).body(message);
                } else {
                    message = "O valor na conta não é suficiente para efetuar o saque";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
                }
            } else {
                message = "Conta não encontrada";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }
        } else {
            message = "Cliente não encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @GetMapping("/query-extract/{clientId}")
    public ResponseEntity<?> queryExtract(@PathVariable Long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        String message;
        if (optionalClient.isPresent()) {
            Set<Transation> transations = services.queryExtract(clientId);
            Account account = optionalClient.get().getAccount();
            if (account != null) {
                if (transations != null) {
                    return ResponseEntity.status(HttpStatus.FOUND).body(transations);
                } else {
                    message = "Essa conta ainda não possui nenhuma transação";
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", message));
                }
            } else {
                message = "Conta não encontrada";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", message));
            }

        } else {
            message = "Cliente não encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", message));
        }
    }

    @GetMapping("/query-limit/{clientId}")
    public ResponseEntity<?> queryLimit(@PathVariable Long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        String message;
        if (optionalClient.isPresent()) {
            Double limit = services.queryLimit(clientId);
            Account account = optionalClient.get().getAccount();
            if (account != null) {
                if (limit != null) {
                    return ResponseEntity.status(HttpStatus.FOUND).body(Map.of("Limit:", limit));
                } else {
                    message = "Conta poupança não pode ter limite de crédito";
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", message));
                }
            } else {
                message = "Conta não encontrada";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", message));
            }

        } else {
            message = "Cliente não encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", message));
        }
    }
}


