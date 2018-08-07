package com.eracambodia.era.service;

import com.eracambodia.era.model.Authority;
import com.eracambodia.era.model.User;
import com.eracambodia.era.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userAuthority")
public class UserAuthority implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    /*@Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User userAccount=userRepository.findUserByUsername(s);
        List<Authority> authorities=new ArrayList<>();
        authorities.add(userAccount.getAuthority());
        org.springframework.security.core.userdetails.User userAuth=
                new org.springframework.security.core.userdetails.User(userAccount.getUsername(),
                        userAccount.getPassword(),authorities);
        return userAuth;
    }*/
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User userAccount=userRepository.findUserByEmail(s);
        List<Authority> authorities=new ArrayList<>();
        authorities.add(userAccount.getAuthority());
        org.springframework.security.core.userdetails.User userAuth=
                new org.springframework.security.core.userdetails.User(userAccount.getEmail(),
                        userAccount.getPassword(),authorities);
        return userAuth;
    }
}
