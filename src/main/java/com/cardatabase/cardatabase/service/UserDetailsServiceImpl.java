package com.cardatabase.cardatabase.service;

import com.cardatabase.cardatabase.domain.AppUser;
import com.cardatabase.cardatabase.domain.AppUserRepository;

import static org.springframework.security.core.userdetails.User.UserBuilder;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        final UserBuilder[] userBuilder = {null};
        appUser.ifPresentOrElse(user -> {
            userBuilder[0] = User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole());
        }, () -> {
            throw new UsernameNotFoundException("");
        });
        return userBuilder[0].build();
    }
}
