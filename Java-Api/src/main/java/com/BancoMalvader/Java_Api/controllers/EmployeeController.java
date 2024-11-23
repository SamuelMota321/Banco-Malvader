package com.BancoMalvader.Java_Api.controllers;

import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.BancoMalvader.Java_Api.repositories.AddressRepository;
import com.BancoMalvader.Java_Api.schemas.EmployeeSchema;
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
    private AddressRepository addressRepository;

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
        // Converter o schema para entidade Employee
        Employee employee = convertToEntity(schema);

        Employee savedEmployee = services.registerEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    // Metodo utilitário para converter o schema em entidade Employee
    private Employee convertToEntity(EmployeeSchema schema) {
        Address address = new Address(
                null,
                schema.getAddress().getZipCode(),
                schema.getAddress().getLocal(),
                schema.getAddress().getHouseNumber(),
                schema.getAddress().getNeighborhood(),
                schema.getAddress().getCity(),
                schema.getAddress().getState()
        );
        addressRepository.save(address);

        return new Employee(
                null,
                schema.getName(),
                schema.getBornDate(),
                schema.getPassword(),
                null, // UserType será atribuído no Service
                schema.getPhone(),
                schema.getCpf(),
                schema.getEmployeeCode(),
                schema.getJob(),
                address
        );
    }


}

