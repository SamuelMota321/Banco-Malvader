package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {}
