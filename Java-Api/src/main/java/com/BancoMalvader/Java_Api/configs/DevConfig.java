//package com.BancoMalvader.Java_Api.configs;
//
//import com.BancoMalvader.Java_Api.entities.account.saving.Saving;
//import com.BancoMalvader.Java_Api.entities.user.Address;
//import com.BancoMalvader.Java_Api.entities.user.UserType;
//import com.BancoMalvader.Java_Api.entities.user.client.Client;
//import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
//import com.BancoMalvader.Java_Api.repositories.AddressRepository;
//import com.BancoMalvader.Java_Api.repositories.ClientRepository;
//import com.BancoMalvader.Java_Api.repositories.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import java.time.Instant;
//import java.util.Arrays;
//
//@Configuration
//@Profile("dev")
//public class DevConfig implements CommandLineRunner {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//    @Autowired
//    private AddressRepository addressRepository;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Employee f1 = new Employee(null, "samuel", Instant.parse("2019-06-20T19:53:07Z"), "35221119", UserType.Funcionario, "392849238", "323423423", "3242xcfffd", null, "desenvolvedor");
//        Address a1 = new Address(null, "213", "dsad", 2, "dfsdrfsd", "weqe", "sdfs", f1);
//        Client c1 = new Client(null, "gustavo", Instant.parse("2019-06-20T19:53:07Z"), "sadas", UserType.Cliente, "fdfs232", "23423423", null);
//        Address a2 = new Address(null, "213", "dsad", 2, "dfsdrfsd", "weqe", "sdfs", c1);
//
//    }
//}
