package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.current.Current;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentRepository extends JpaRepository<Current, Long> {
    Optional<Current> findByAccountNumber(int accountNumber);
}
