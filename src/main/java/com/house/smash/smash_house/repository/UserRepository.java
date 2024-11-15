package com.house.smash.smash_house.repository;

import com.house.smash.smash_house.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.nickname = :nickname")
    Optional<User> findByNickname(@Param("nickname") String nickname); // Método para buscar usuario por nickname
}
