package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.account.current.Current;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentRepository extends JpaRepository<Current, Long> {}
