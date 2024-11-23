package com.BancoMalvader.Java_Api.services;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.AccountType;
import com.BancoMalvader.Java_Api.entities.account.current.Current;
import com.BancoMalvader.Java_Api.entities.account.saving.Saving;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.BancoMalvader.Java_Api.repositories.AccountRepository;
import com.BancoMalvader.Java_Api.repositories.ClientRepository;
import com.BancoMalvader.Java_Api.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployeeServices {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;


    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        Optional<Employee> obj = employeeRepository.findById(id);
        return obj.get();
    }

    public Employee registerEmployee(Employee employee) {
        employee.setUserType(UserType.Funcionario);
        return employeeRepository.save(employee);

    }

    public Account createAccountForClient(Long clientId, String accountType, Double initialBalance, String agency, Double limitt, Instant maturity, Double yieldRate) {
        // Verifica se o cliente existe
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new RuntimeException("Client not found");
        }

        Client client = clientOptional.get();
        Account account;
        Integer accountNumber = generateUniqueAccountNumber();
        if ("conta_corrente".equals(accountType)) {
            account = new Current(null, AccountType.Conta_corrente, initialBalance, accountNumber, agency, client, limitt, maturity); // Supondo que 'Current' seja uma subclasse de 'Account'
        } else {
            account = new Saving(null, AccountType.Conta_Poupanca, initialBalance, accountNumber, agency, client, yieldRate); // Supondo que 'Saving' seja uma subclasse de 'Account'
        }
        return accountRepository.save(account);
    }

    private Integer generateUniqueAccountNumber() {
        Random random = new Random();
        int accountNumber;
        do {
            accountNumber = 100000 + random.nextInt(900000);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    public void deleteAccount(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (!accountOptional.isPresent()) {
            throw new RuntimeException("Account not found");
        }
        Account account = accountOptional.get();
        accountRepository.delete(account);

    }

    public Account queryAccountData(int accountNumber) {
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        return accountOptional.get();
    }

    public Client queryClientData(Long idClient) {
        Optional<Client> clientOptional = clientRepository.findById(idClient);
        return clientOptional.get();
    }
}