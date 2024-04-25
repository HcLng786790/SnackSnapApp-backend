package com.huuduc.snacksnap.security;

import com.huuduc.snacksnap.data.entity.User;
import com.huuduc.snacksnap.repository.UserReopsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserReopsitory userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = this.userRepository.findByEmail(username);

        if (user.isPresent()) {
            return new CustomUserDetails(user.get());
        } else {
            throw new RuntimeException("User not found");
        }

    }
}
