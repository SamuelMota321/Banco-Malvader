package com.BancoMalvader.Java_Api.entities.operations;

import com.BancoMalvader.Java_Api.entities.user.employee.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.Insert;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "relatorio")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Relatory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Instant dateGeneration;
    private List<String> content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Employee employee;

    public Relatory(Long id, String type, List<String> content, Instant dateGeneration) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.dateGeneration = dateGeneration;
    }

    public void generateGeneralReport() {
        //implementação da lógica aqui
    }

    public void exportToExcel() {
    }

}
