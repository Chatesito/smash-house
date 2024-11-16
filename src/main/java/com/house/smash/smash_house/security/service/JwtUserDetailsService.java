package com.house.smash.smash_house.security.service;

import com.house.smash.smash_house.model.Role;
import com.house.smash.smash_house.model.User;
import com.house.smash.smash_house.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + nickname));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            System.out.println("Añadiendo rol: ROLE_" + role.getName());
        }

        return new org.springframework.security.core.userdetails.User(
                user.getNickname(),
                user.getPassword(),
                grantedAuthorities
        );
    }
}