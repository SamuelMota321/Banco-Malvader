package com.BancoMalvader.Java_Api.services;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.current.Current;
import com.BancoMalvader.Java_Api.entities.operations.Transation;
import com.BancoMalvader.Java_Api.entities.operations.TransationType;
import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.AddressResquestDTO;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.entities.user.client.ClientRequestDTO;
import com.BancoMalvader.Java_Api.exceptions.ClientNotFoundException;
import com.BancoMalvader.Java_Api.exceptions.InsufficientBalanceException;
import com.BancoMalvader.Java_Api.exceptions.LimitExcepton;
import com.BancoMalvader.Java_Api.exceptions.TransactionsNotFoundException;
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
    private AddressRepository addressRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
    }

    public Double getBalance(Client client) {
        Account account = Optional.ofNullable(client.getAccount())
                .orElseThrow(ClientNotFoundException::new);

        return account.getBalance();
    }

    public Set<Transation> queryExtract(Client client) {
        Account account = Optional.ofNullable(client.getAccount()).
                orElseThrow(ClientNotFoundException::new);

        if (account.getTransations().isEmpty()) throw new TransactionsNotFoundException("Usuário não possui historico de transações.");

        return account.getTransations();

    }

    public Double queryLimit(Client client) {
        Account account = Optional.ofNullable(client.getAccount()).
                orElseThrow(ClientNotFoundException::new);

        if (account instanceof Current) return ((Current) account).getLimitt();

        throw new LimitExcepton("Conta poupança não pode ter limite de crédito");
    }

    @Transactional
    public void transferency(Client client, TransferencySchema schema) {
        Account senderAccount = Optional.ofNullable(client.getAccount()).
                orElseThrow(ClientNotFoundException::new);
        Double value = schema.getValue();

        Account receiverAccount = accountRepository.findByAccountNumber(schema.getAccountNumber())
                .orElseThrow(ClientNotFoundException::new);

        if (senderAccount.getBalance() < value) {
            throw new InsufficientBalanceException();
        }

        Instant hour = Instant.now();
        Transation transation = new Transation(null, hour, value, TransationType.Transferencia, senderAccount);

        senderAccount.debit(value);
        receiverAccount.credit(value);

        transationRepository.save(transation);
        accountRepository.saveAll(Arrays.asList(senderAccount, receiverAccount));
    }

    @Transactional
    public Client registerClient(ClientRequestDTO dataClient, AddressResquestDTO dataAddress) {
        Address address = new Address(dataAddress);
        addressRepository.save(address);
        Client client = new Client(dataClient, address, UserType.Cliente);
        clientRepository.save(client);
        return client;
    }

    @Transactional
    public Account deposit(Client client, Double value) {
        Account account = Optional.ofNullable(client.getAccount())
                .orElseThrow(ClientNotFoundException::new);

        Instant hour = Instant.now();
        Transation transation = new Transation(null, hour, value, TransationType.Deposito, account);

        account.credit(value);

        transationRepository.save(transation);
        accountRepository.save(account);

        return account;
    }

    @Transactional
    public Account withdraw(Client client, Double value) {
        Account account = Optional.ofNullable(client.getAccount())
                .orElseThrow(ClientNotFoundException::new);

        Instant hour = Instant.now();
        Transation transation = new Transation(null, hour, value, TransationType.Saque, account);

        account.debit(value);

        transationRepository.save(transation);
        accountRepository.save(account);

        return account;
    }

}