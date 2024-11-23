package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {}
