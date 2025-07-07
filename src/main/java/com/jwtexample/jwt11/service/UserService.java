package com.jwtexample.jwt11.service;
import com.jwtexample.jwt11.repo.UserRepository;
import com.jwtexample.jwt11.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;



import com.jwtexample.jwt11.entity.User;
import com.jwtexample.jwt11.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }


    // JWT kontrolünde kullanılır
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + username);
        }

        User user = userOpt.get();

        // Kullanıcıyı Spring Security'nin anlayacağı formata dönüştür
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }



    // Register için kullanılacak
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
