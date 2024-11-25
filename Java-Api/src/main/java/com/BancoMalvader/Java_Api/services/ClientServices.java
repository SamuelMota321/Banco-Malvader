package com.BancoMalvader.Java_Api.services;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.AccountType;
import com.BancoMalvader.Java_Api.entities.account.current.Current;
import com.BancoMalvader.Java_Api.entities.operations.Transation;
import com.BancoMalvader.Java_Api.entities.operations.TransationType;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.repositories.AccountRepository;
import com.BancoMalvader.Java_Api.repositories.ClientRepository;
import com.BancoMalvader.Java_Api.repositories.TransationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        Optional<Client> obj = clientRepository.findById(id);
        return obj.get();
    }

    public Client registerClient(Client client) {
        client.setUserType(UserType.Cliente);
        return clientRepository.save(client);
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
                    Current current = (Current) account;
                    return ((Current) account).getLimitt();
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}