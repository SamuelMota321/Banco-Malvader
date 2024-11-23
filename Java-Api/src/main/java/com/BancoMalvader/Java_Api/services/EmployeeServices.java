package com.BancoMalvader.Java_Api.services;

import com.BancoMalvader.Java_Api.entities.user.UserType;
import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.BancoMalvader.Java_Api.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServices {
    @Autowired
    private EmployeeRepository repository;


    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee findById(Long id) {
        Optional<Employee> obj = repository.findById(id);
        return obj.get();
    }

    public Employee registerEmployee(Employee employee) {
        employee.setUserType(UserType.Funcionario);
        return repository.save(employee);

    }

}