package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.operations.Transation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransationRepository extends JpaRepository<Transation, Long> {}
