package com.BancoMalvader.Java_Api.services;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.AccountType;
import com.BancoMalvader.Java_Api.entities.account.current.Current;
import com.BancoMalvader.Java_Api.entities.operations.Transation;
import com.BancoMalvader.Java_Api.entities.operations.TransationType;
import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.AddressResquestDTO;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.entities.user.client.ClientRequestDTO;
import com.BancoMalvader.Java_Api.entities.user.employee.EmployerRequestDTO;
import com.BancoMalvader.Java_Api.repositories.AccountRepository;
import com.BancoMalvader.Java_Api.repositories.AddressRepository;
import com.BancoMalvader.Java_Api.repositories.ClientRepository;
import com.BancoMalvader.Java_Api.repositories.TransationRepository;
import com.BancoMalvader.Java_Api.schemas.TransferencySchema;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClientServices {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransationRepository transationRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private AddressRepository addressRepository;

    private Client instantiateClient(ClientRequestDTO dataClient, Address address) {
        Client client = new Client();

        client.setName(dataClient.name());
        client.setBornDate(dataClient.bornDate());
        client.setPassword(dataClient.password());
        client.setUserType(UserType.Cliente);
        client.setPhone(dataClient.phone());
        client.setCPF(dataClient.CPF());
        client.setAddress(address);

        return client;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        Optional<Client> obj = clientRepository.findById(id);
        return obj.get();
    }

    public Client registerClient(ClientRequestDTO dataClient, AddressResquestDTO dataAddress) {
        Address address = userServices.instantiateAddress(dataAddress);
        addressRepository.save(address);
        Client client = instantiateClient(dataClient, address);
        clientRepository.save(client);
        return client;
    }

    public Double queryBalance(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            if (client.getAccount() != null) {
                Account account = client.getAccount();
                return account.getBalance();
            }
        }
        return null;
    }

    public Account deposit(Long clientId, Double value) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            if (client.getAccount() != null) {
                Account account = client.getAccount();
                Instant hour = Instant.now();
                Transation transation = new Transation(null, hour, value, TransationType.Deposito, account);
                transationRepository.save(transation);
                double atualBalance = account.getBalance();
                double setBalance = atualBalance + value;
                account.setBalance(setBalance);
                return accountRepository.save(account);
            }
        }
        return null;
    }

    public Account withdraw(Long clientId, Double value) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            if (client.getAccount() != null) {
                Account account = client.getAccount();
                Instant hour = Instant.now();
                Transation transation = new Transation(null, hour, value, TransationType.Saque, account);
                transationRepository.save(transation);
                double atualBalance = account.getBalance();
                double setBalance = atualBalance - value;
                account.setBalance(setBalance);
                return accountRepository.save(account);
            }
        }
        return null;
    }

    public Set<Transation> queryExtract(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            if (client.getAccount() != null) {
                Account account = client.getAccount();
                return account.getTransations();
            }
        }
        return null;
    }

    public Double queryLimit(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            if (client.getAccount() != null) {
                Account account = client.getAccount();
                if (account.getAccountType() == AccountType.Conta_corrente) {
                    return ((Current) account).getLimitt();
                } else {
                    return null;
                }
            }
        }
        return null;

    }

    @Transactional
    public void transferency(Client client, TransferencySchema schema) {
        Account senderAccount = Optional.ofNullable(client.getAccount()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        Double value = schema.getValue();

        Account receiverAccount = accountRepository.findByAccountNumber(schema.getAccountNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        if (senderAccount.getBalance() < value) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado");
        }

        Instant hour = Instant.now();
        Transation transation = new Transation(null, hour, value, TransationType.Transferencia, senderAccount);

        senderAccount.debit(value);
        receiverAccount.credit(value);

        transationRepository.save(transation);
        accountRepository.saveAll(Arrays.asList(senderAccount, receiverAccount));
    }

}