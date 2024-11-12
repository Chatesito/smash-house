package com.house.smash.smash_house.repository;

import com.house.smash.smash_house.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Método para buscar usuario por email
}
