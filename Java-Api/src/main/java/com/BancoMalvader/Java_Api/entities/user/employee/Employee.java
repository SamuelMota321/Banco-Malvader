package com.BancoMalvader.Java_Api.entities.user.employee;

import com.BancoMalvader.Java_Api.entities.operations.Relatory;
import com.BancoMalvader.Java_Api.entities.user.Address;
import com.BancoMalvader.Java_Api.entities.user.User;
import com.BancoMalvader.Java_Api.entities.user.UserType;
import com.BancoMalvader.Java_Api.schemas.EmployeeSchema;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "funcionarios")
@NoArgsConstructor
@Getter
@Setter
public class Employee extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "employee")
    private final Set<Relatory> relatory = new HashSet<>();
    private String employeeCode;
    private String job;

    public Employee(Long id, String name, Instant bornDate, String password, UserType userType, String phone, String CPF, String employeeCode, String job, Address address) {
        super(id, name, bornDate, password, userType, phone, CPF, address);
        this.employeeCode = employeeCode;
        this.job = job;
    }

//    public void generateMovementReport() {
//        //implementação da lógica aqui
//    }
}
