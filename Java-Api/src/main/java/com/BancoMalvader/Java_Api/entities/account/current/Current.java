//package com.BancoMalvader.Java_Api.entities.account.current;
//
//import com.BancoMalvader.Java_Api.entities.account.Account;
//import com.BancoMalvader.Java_Api.entities.account.AccountType;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDate;
//
//@Getter
//public class Current extends Account {
//    private int id;
//
//    @Setter
//    private double limit;
//
//    @Setter
//    private LocalDate maturity;
//
//    public Current(int id, int accountNumber, String agency, double balance, AccountType type, double limit, LocalDate maturity) {
//        super(id, accountNumber, agency, balance, type);
//        this.limit = limit;
//        this.maturity = maturity;
//    }
//
//    public double queryLimit() {
//        // implementação da lógica aqui
//        return 0;
//    }
//}
