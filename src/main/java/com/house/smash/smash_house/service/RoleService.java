package com.house.smash.smash_house.service;

import com.house.smash.smash_house.model.Role;
import com.house.smash.smash_house.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Obtener un rol por su nombre
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
