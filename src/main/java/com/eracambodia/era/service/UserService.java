package com.eracambodia.era.service;

import com.eracambodia.era.model.Authority;
import com.eracambodia.era.model.User;

import java.util.List;

public interface UserService {
    User register(User user);
    User userAvailable(User user);
    User findUserByName(String username);
    User findUserByEmail(String email);
}
