package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
