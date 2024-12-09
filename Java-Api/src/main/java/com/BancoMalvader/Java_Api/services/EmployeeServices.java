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
import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.BancoMalvader.Java_Api.entities.user.employee.EmployerRequestDTO;
import com.BancoMalvader.Java_Api.repositories.*;
import com.BancoMalvader.Java_Api.schemas.AccountSchema;
import com.BancoMalvader.Java_Api.schemas.ClientSchema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
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

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserServices userServices;


    @PersistenceContext
    private EntityManager entityManager;

    private static Address getAddress(ClientSchema schema, Client client) {
        Address address = client.getAddress();

        if (address.getZipCode() != null)
            address.setZipCode(schema.getAddress().getZipCode());

        if (address.getLocal() != null)
            address.setLocal(schema.getAddress().getLocal());

        if (address.getHouseNumber() != null)
            address.setHouseNumber(schema.getAddress().getHouseNumber());

        if (address.getNeighborhood() != null)
            address.setNeighborhood(schema.getAddress().getNeighborhood());

        if (address.getCity() != null)
            address.setCity(schema.getAddress().getCity());

        if (address.getState() != null)
            address.setState(schema.getAddress().getState());
        return address;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        Optional<Employee> obj = employeeRepository.findById(id);
        return obj.get();
    }


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

    public Employee registerEmployee(AddressResquestDTO dataAddress, EmployerRequestDTO dataEmployer) {
        Address address = userServices.instantiateAddress(dataAddress);
        addressRepository.save(address);

        Employee employee = instantiateEmployee(dataEmployer, address);

        employeeRepository.save(employee);

        return employee;
    }

    public Account createAccountForClient(Long clientId,AccountRequestDTO dataAccount) {
        // Verifica se o cliente existe
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isEmpty()) {
            throw new RuntimeException("Client not found");
        }

        Client client = clientOptional.get();
        Account account;
        Integer accountNumber = generateUniqueAccountNumber();
        if ("conta_corrente".equals(dataAccount.accountType())) {
            account = new Current(null, AccountType.Conta_corrente, dataAccount.balance(), accountNumber, dataAccount.agency(), client, dataAccount.limitt(), dataAccount.maturity()); // Supondo que 'Current' seja uma subclasse de 'Account'
        } else {
            account = new Saving(null, AccountType.Conta_Poupanca, dataAccount.balance(), accountNumber, dataAccount.agency(), client, dataAccount.yieldRate()); // Supondo que 'Saving' seja uma subclasse de 'Account'
        }
        accountRepository.save(account);
        return account;
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
        // Verifica se o ID está em conta_corrente
        Long count = ((Number) entityManager.createNativeQuery("SELECT COUNT(*) FROM conta_corrente WHERE id = :id")
                .setParameter("id", accountId)
                .getSingleResult()).longValue();

        if (count > 0) {
            entityManager.createNativeQuery("DELETE FROM conta_corrente WHERE id = :id")
                    .setParameter("id", accountId)
                    .executeUpdate();
            return;
        }

        // Verifica se o ID está em conta_poupanca
        count = ((Number) entityManager.createNativeQuery("SELECT COUNT(*) FROM conta_poupanca WHERE id = :id")
                .setParameter("id", accountId)
                .getSingleResult()).longValue();

        if (count > 0) {
            entityManager.createNativeQuery("DELETE FROM conta_poupanca WHERE id = :id")
                    .setParameter("id", accountId)
                    .executeUpdate();
            return;
        }

        throw new EntityNotFoundException("Conta com ID " + accountId + " não encontrada.");
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
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        Account account = accountOptional.get();

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
        Current current = currentRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Conta corrente com número " + accountNumber + " não encontrada."));

        if (schema.getInitialBalance() != null)
            current.setBalance(schema.getInitialBalance());
        if (schema.getLimitt() != null)
            current.setLimitt(schema.getLimitt());
        if (schema.getMaturity() != null)
            current.setMaturity(schema.getMaturity());
        currentRepository.save(current);
    }

    private void updateSavingAccount(AccountSchema schema, int accountNumber) {
        Saving saving = savingRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Conta poupança com número " + accountNumber + " não encontrada."));

        if (schema.getInitialBalance() != null)
            saving.setBalance(schema.getInitialBalance());

        if (schema.getYieldRate() != null)
            saving.setYieldRate(schema.getYieldRate());
        savingRepository.save(saving);
    }

    public void alterClientData(Long clientId, ClientSchema schema) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client client = optionalClient.orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + clientId + " não encontrado."));

        if (schema.getName() != null)
            client.setName(schema.getName());

        if (schema.getCpf() != null)
            client.setCPF(schema.getCpf());

        if (schema.getPhone() != null)
            client.setPhone(schema.getPhone());

        if (schema.getPassword() != null)
            client.setPassword(schema.getPassword());

        if (schema.getBornDate() != null)
            client.setBornDate(schema.getBornDate());

        if (schema.getAddress() != null) {
            Address address = getAddress(schema, client);
            addressRepository.save(address);
        }
        clientRepository.save(client);
    }

    public Employee employeeRegisterEmployee(Employee newEmployee, Long employeeId, String password) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        Employee employee = employeeOptional.get();
        if (employee.getPassword().equals(password)) {
            newEmployee.setUserType(UserType.Funcionario);
            return employeeRepository.save(newEmployee);
        } else {
            throw new IllegalArgumentException("Senha incorreta");
        }

    }


}