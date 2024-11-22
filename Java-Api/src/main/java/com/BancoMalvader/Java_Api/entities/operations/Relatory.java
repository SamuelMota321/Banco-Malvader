package com.BancoMalvader.Java_Api.entities.operations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Relatory {
    private int id;
    private String type;
    private LocalDateTime dateGeneration;
    private List<String> content;


    public void generateGeneralReport() {
        //implementação da lógica aqui
    }

    public void exportToExcel() {
    }

}
