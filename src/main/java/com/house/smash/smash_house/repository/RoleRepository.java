package com.house.smash.smash_house.repository;

import com.house.smash.smash_house.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository {
    Optional<Role> findByName(String name); // Método para buscar roles por nombre
}
