package com.eracambodia.era.service;

import com.eracambodia.era.model.Authority;
import com.eracambodia.era.model.User;
import com.eracambodia.era.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void updateUserPassword(User user) {
        userRepository.updateUserPassword(user);
    }

    @Override
    public void updateUserInformation(User user) {
        userRepository.updateUserPassword(user);
    }

    @Override
    public void updateImageProfile(String image,String email) {
        userRepository.updateImageProfile(image,email);
    }

    @Override
    public User userAvailable(User user) {
        return userRepository.userAvailable(user);
    }

    @Override
    public User register(User user) {
        userRepository.register(user);
        return user;
    }
}
