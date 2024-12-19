package com.BancoMalvader.Java_Api.services;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.AccountRequestDTO;
import com.BancoMalvader.Java_Api.entities.account.AccountType;
import com.BancoMalvader.Java_Api.entities.account.current.Current;
import com.BancoMalvader.Java_Api.entities.account.saving.Saving;
import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.AddressResquestDTO;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.entities.user.client.ClientRequestDTO;
import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.BancoMalvader.Java_Api.entities.user.employee.EmployerRequestDTO;
import com.BancoMalvader.Java_Api.repositories.*;
import com.BancoMalvader.Java_Api.schemas.AccountSchema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserServices userServices;

    @PersistenceContext
    private EntityManager entityManager;

    private Employee instantiateEmployee(EmployerRequestDTO dataEmployer, Address address) {
        Employee employee = new Employee();

        employee.setName(dataEmployer.nome());
        employee.setBornDate(dataEmployer.bornDate());
        employee.setPassword(dataEmployer.password());
        employee.setUserType(UserType.Funcionario);
        employee.setPhone(dataEmployer.phone());
        employee.setCPF(dataEmployer.cpf());
        employee.setAddress(address);
        employee.setJob(dataEmployer.job());
        employee.setEmployeeCode(dataEmployer.employerCode());

        return employee;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
    }

    public Employee registerEmployee(AddressResquestDTO dataAddress, EmployerRequestDTO dataEmployer) {
        Address address = userServices.instantiateAddress(dataAddress);
        addressRepository.save(address);
        Employee employee = instantiateEmployee(dataEmployer, address);
        employeeRepository.save(employee);

        return employee;
    }

    public Account createAccountForClient(Long clientId, AccountRequestDTO dataAccount) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        Account account;
        Integer accountNumber = generateUniqueAccountNumber();
        if ("conta_corrente".equals(dataAccount.accountType())) {
            account = new Current(null, AccountType.Conta_corrente, dataAccount.balance(), accountNumber, dataAccount.agency(), client, dataAccount.limitt(), dataAccount.maturity());
        } else {
            account = new Saving(null, AccountType.Conta_Poupanca, dataAccount.balance(), accountNumber, dataAccount.agency(), client, dataAccount.yieldRate());
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
        long count = ((Number) entityManager.createNativeQuery("SELECT COUNT(*) FROM conta_corrente WHERE id = :id").setParameter("id", accountId).getSingleResult()).longValue();

        if (count > 0) {
            entityManager.createNativeQuery("DELETE FROM conta_corrente WHERE id = :id").setParameter("id", accountId).executeUpdate();
            return;
        }

        // Verifica se o ID está em conta_poupanca
        count = ((Number) entityManager.createNativeQuery("SELECT COUNT(*) FROM conta_poupanca WHERE id = :id").setParameter("id", accountId).getSingleResult()).longValue();

        if (count > 0) {
            entityManager.createNativeQuery("DELETE FROM conta_poupanca WHERE id = :id").setParameter("id", accountId).executeUpdate();
            return;
        }

        throw new EntityNotFoundException("Conta com ID " + accountId + " não encontrada.");
    }

    public Account queryAccountData(int accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

    }

    public Client queryClientData(Long idClient) {
        return clientRepository.findById(idClient).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

    }

    public void alterAccountData(AccountSchema schema, int accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
        AccountType accountType = account.getAccountType();

        if (accountType == AccountType.Conta_corrente) {
            updateCurrentAccount(schema, accountNumber);
        } else if (accountType == AccountType.Conta_Poupanca) {
            updateSavingAccount(schema, accountNumber);
        } else {
            throw new IllegalArgumentException("Tipo de conta desconhecido: " + accountType);
        }
    }

    private void updateCurrentAccount(AccountSchema schema, int accountNumber) {
        Current current = currentRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException("Conta corrente com número " + accountNumber + " não encontrada."));

        if (schema.getInitialBalance() != null) current.setBalance(schema.getInitialBalance());
        if (schema.getLimitt() != null) current.setLimitt(schema.getLimitt());
        if (schema.getMaturity() != null) current.setMaturity(schema.getMaturity());
        currentRepository.save(current);
    }

    private void updateSavingAccount(AccountSchema schema, int accountNumber) {
        Saving saving = savingRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException("Conta poupança com número " + accountNumber + " não encontrada."));

        if (schema.getInitialBalance() != null) saving.setBalance(schema.getInitialBalance());

        if (schema.getYieldRate() != null) saving.setYieldRate(schema.getYieldRate());
        savingRepository.save(saving);
    }

    public void alterClientData(Long clientId, AddressResquestDTO dataAddress, ClientRequestDTO dataClient) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + clientId + " não encontrado."));
        client.update(dataClient);
        client.getAddress().update(dataAddress);
        clientRepository.save(client);
    }


    public Employee employeeRegisterEmployee(EmployerRequestDTO dataEmployer, AddressResquestDTO dataAddress, Long employeeId, String password) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        if (employee.getPassword().equals(password)) {
            Address address = userServices.instantiateAddress(dataAddress);
            addressRepository.save(address);
            Employee newEmployee = instantiateEmployee(dataEmployer, address);
            newEmployee.setUserType(UserType.Funcionario);
            return employeeRepository.save(newEmployee);
        } else {
            throw new IllegalArgumentException("Senha incorreta");
        }


    }
}