package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.account.saving.Saving;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingRepository extends CrudRepository<Saving, Long> {
    Optional<Saving> findByAccountNumber(int accountNumber);

}
