package com.eracambodia.era.service.user;

import com.eracambodia.era.model.user.User;

public interface UserService {
    User register(User user);
    User userAvailable(User user);
    User findUserByName(String username);
    Integer findUserIdById(int agentId);
    User findUserByEmail(String email);
    void updateImageProfile(String image,String email);
    void updateUserPassword(User user);
    void updateUserInformation(User user);
}
