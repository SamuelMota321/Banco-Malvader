package com.BancoMalvader.Java_Api.configs;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.AccountType;
import com.BancoMalvader.Java_Api.entities.account.current.Current;
import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import com.BancoMalvader.Java_Api.entities.user.client.Client;
import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.BancoMalvader.Java_Api.repositories.AccountRepository;
import com.BancoMalvader.Java_Api.repositories.AddressRepository;
import com.BancoMalvader.Java_Api.repositories.ClientRepository;
import com.BancoMalvader.Java_Api.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
//        Address a1 = new Address(null, "cliete id 1", 2, "sdfs", "dfsdrfsd", "sdfs", "dfs");
//        Address a2 = new Address(null, "funcionario id 2", 2, "sdfs", "dfsdrfsd", "sdfs", "c1");
//
//        Employee f1 = new Employee(null, "samuel", "231239887", "35221119", "saewq213231", a1, UserType.Funcionario, "323423423", "Dev", Instant.parse("2019-06-20T19:53:07Z"));
//        Client c1 = new Client(null, "gustavo", Instant.parse("2019-06-20T19:53:07Z"), "sadas", a2, UserType.Cliente, "fdfs232", "23423423");
//
//        Account ac1 = new Current(null, AccountType.Conta_corrente, 2133.43, 23, "a2321", c1, 1233.33, Instant.parse("2019-06-20T19:53:07Z"));
//
//        c1.setAccount(ac1);
//
//        addressRepository.saveAll(Arrays.asList(a1, a2));
//        clientRepository.save(c1);
//        employeeRepository.save(f1);
//        accountRepository.save(ac1);

    }
}
