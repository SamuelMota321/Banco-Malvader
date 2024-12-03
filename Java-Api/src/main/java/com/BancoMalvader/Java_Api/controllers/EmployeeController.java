package com.BancoMalvader.Java_Api.controllers;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.AccountRequestDTO;
import com.BancoMalvader.Java_Api.entities.user.AddressResquestDTO;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.BancoMalvader.Java_Api.entities.user.employee.EmployerRequestDTO;
import com.BancoMalvader.Java_Api.schemas.AccountSchema;
import com.BancoMalvader.Java_Api.schemas.ClientSchema;
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
        AddressResquestDTO addressResquestDTO = new AddressResquestDTO(schema.getAddress().getZipCode(), schema.getAddress().getLocal(), schema.getAddress().getHouseNumber(), schema.getAddress().getNeighborhood(), schema.getAddress().getCity(), schema.getAddress().getState());
        EmployerRequestDTO employerRequestDTO = new EmployerRequestDTO(schema.getName(), schema.getCpf(), schema.getPhone(), schema.getPassword(), schema.getJob(), schema.getBornDate(), schema.getEmployeeCode());

        Employee savedEmployee = services.registerEmployee(addressResquestDTO, employerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    // Metodo utilitário para converter o schema em entidade Employee
    @PostMapping("/criar-conta/{clientId}")
    public ResponseEntity<?> createAccount(@PathVariable Long clientId, @RequestBody AccountSchema schema) {
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO(schema.getAgency(), schema.getInitialBalance(), schema.getAccountType(), schema.getYieldRate(), schema.getLimitt(), schema.getMaturity());
        Account account = services.createAccountForClient(clientId,accountRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
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
    public ResponseEntity<String> updateAccount(@RequestBody AccountSchema schema, @PathVariable int accountNumber) {
        services.alterAccountData(schema, accountNumber);
        String successMessage = "Conta de número " + accountNumber + " atualizada com sucesso!";
        return ResponseEntity.ok(successMessage);
    }

    @PatchMapping("/update-client/{clientId}")
    public ResponseEntity<String> updateClient(@RequestBody ClientSchema clientSchema, @PathVariable Long clientId) {
        services.alterClientData(clientId, clientSchema);
        return ResponseEntity.ok("Cliente atualizado com sucesso!");
    }

    @PostMapping("/register/{employeeId}/{password}")
    public ResponseEntity<Employee> employeeRegisterEmployee(@Valid @RequestBody EmployeeSchema schema, @PathVariable Long employeeId, @PathVariable String password) {
        Employee employee = bodyParserServices.convertToEntity(schema);
        Employee savedEmployee = services.employeeRegisterEmployee(employee, employeeId, password);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);

    }

}


