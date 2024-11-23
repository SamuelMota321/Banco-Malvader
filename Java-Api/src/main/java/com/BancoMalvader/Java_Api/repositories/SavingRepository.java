package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.account.saving.Saving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingRepository extends JpaRepository<Saving, Long> {}
