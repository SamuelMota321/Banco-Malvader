package com.BancoMalvader.Java_Api.services;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.AccountType;
import com.BancoMalvader.Java_Api.entities.account.current.Current;
import com.BancoMalvader.Java_Api.entities.account.saving.Saving;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.BancoMalvader.Java_Api.repositories.*;
import com.BancoMalvader.Java_Api.schemas.AccountSchema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    private CurrentRepository currentRepository;
    @Autowired
    private SavingRepository savingRepository;

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

    @Transactional
    public void deleteAccount(Long accountId) {
        // Verifica se a conta existe
        Current current = currentRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Conta com ID " + accountId + " não encontrada."));

        currentRepository.delete(current);
    }



    public Account queryAccountData(int accountNumber) {
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        return accountOptional.get();
    }

    public Client queryClientData(Long idClient) {
        Optional<Client> clientOptional = clientRepository.findById(idClient);
        return clientOptional.get();
    }


    public void alterAccountData(AccountSchema schema, int accountNumber) {
        if ("conta_corrente".equals(schema.getAccountType())) {
            Optional<Current> currentOptional = currentRepository.findByAccountNumber(accountNumber);
            Current current = currentOptional.get();
            current.setAccountType(AccountType.Conta_corrente);
            current.setBalance(schema.getInitialBalance());
            current.setLimitt(schema.getLimitt());
            current.setMaturity(schema.getMaturity());
            currentRepository.save(current);

        } else {
            Optional<Saving> savingOptional = savingRepository.findByAccountNumber(accountNumber);
            Saving saving = savingOptional.get();
            saving.setAccountType(AccountType.Conta_Poupanca);
            saving.setBalance(schema.getInitialBalance());
            saving.setYieldRate(schema.getLimitt());
            savingRepository.save(saving);
        }
    }

    public void alterClientData(Client client) {

    }



}