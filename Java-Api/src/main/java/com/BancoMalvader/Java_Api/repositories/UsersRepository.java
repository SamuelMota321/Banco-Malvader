package com.BancoMalvader.Java_Api.repositories;

import com.BancoMalvader.Java_Api.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
//    Optional<User> findByCpf(String cpf); // Metodo para verificar se o CPF já existe

}
