package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.user.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}
