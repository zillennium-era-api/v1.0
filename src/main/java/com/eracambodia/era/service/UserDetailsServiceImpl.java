package com.eracambodia.era.service;

import com.eracambodia.era.model.GrantedAuthorityImpl;
import com.eracambodia.era.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private com.eracambodia.era.service.Service service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userAccount = service.findUserByEmailOfLogin(email);
        List<GrantedAuthorityImpl> authorities = new ArrayList<>();
        authorities.add(userAccount.getAuthority());
        org.springframework.security.core.userdetails.User userAuth =
                new org.springframework.security.core.userdetails.User(userAccount.getEmail(),
                        userAccount.getPassword(), authorities);
        return userAuth;
    }
}