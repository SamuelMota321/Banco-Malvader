package com.BancoMalvader.Java_Api.controllers;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.BancoMalvader.Java_Api.schemas.AccountSchema;
import com.BancoMalvader.Java_Api.schemas.EmployeeSchema;
import com.BancoMalvader.Java_Api.services.BodyParserServices;
import com.BancoMalvader.Java_Api.services.EmployeeServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/funcionarios")
public class EmployeeController {

    @Autowired
    private EmployeeServices services;

    @Autowired
    private BodyParserServices bodyParserServices;

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> list = services.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        Employee obj = services.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Employee> registerEmployee(@Valid @RequestBody EmployeeSchema schema) {
        // Converter o schema para entidade Employee
        Employee employee = bodyParserServices.convertToEntity(schema);
        Employee savedEmployee = services.registerEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    // Metodo utilitário para converter o schema em entidade Employee
    @PostMapping("/criar-conta/{clientId}")
    public Account createAccount(@PathVariable Long clientId, @RequestBody AccountSchema accountSchema) {
        return services.createAccountForClient(
                clientId,
                accountSchema.getAccountType(),
                accountSchema.getInitialBalance(),
                accountSchema.getAgency(),
                accountSchema.getLimitt(),
                accountSchema.getMaturity(),
                accountSchema.getYieldRate());
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        try {
            services.deleteAccount(accountId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/query-account/{accountNumber}")
    public ResponseEntity<Account> queryAccount(@PathVariable int accountNumber) {
        try {
            Account account = services.queryAccountData(accountNumber);
            return ResponseEntity.ok(account);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/query-client/{clientId}")
    public ResponseEntity<Client> queryClientData(@PathVariable Long clientId) {
        try {
            Client client = services.queryClientData(clientId);
            return ResponseEntity.ok(client);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/update-account/{accountNumber}")
    public ResponseEntity<Void> updateAccount(@RequestBody AccountSchema schema, @PathVariable int accountNumber) {
        services.alterAccountData(schema, accountNumber);
        return ResponseEntity.ok().build();
    }
}

