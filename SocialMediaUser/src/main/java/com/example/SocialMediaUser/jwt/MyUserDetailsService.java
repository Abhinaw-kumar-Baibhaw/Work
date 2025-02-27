package com.example.SocialMediaUser.jwt;

import com.example.SocialMediaUser.model.Users;
import com.example.SocialMediaUser.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users userByEmail = usersRepo.findByEmail(username);
        return new UsersDetails(userByEmail);
    }
}
