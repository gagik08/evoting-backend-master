package com.capstone.epam.evotingsystem.service;

import com.capstone.epam.evotingsystem.entity.User;

public interface UserService {

    User loadUserByUsername(String username);
    User createUser(String username, String password, Integer age, String userRole);
    void assignRoleToUser(String username, String roleName);
    String getUserRoleName(String username);
}
