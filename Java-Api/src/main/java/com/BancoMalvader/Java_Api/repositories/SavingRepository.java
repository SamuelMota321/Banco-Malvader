package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.account.Account;
import com.BancoMalvader.Java_Api.entities.account.saving.Saving;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavingRepository extends JpaRepository<Saving, Long> {
    Optional<Saving> findByAccountNumber(int accountNumber);

}
