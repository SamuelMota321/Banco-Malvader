package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(Integer accountNumber);
    Optional<Account> findByAccountNumber(int accountNumber);
}
