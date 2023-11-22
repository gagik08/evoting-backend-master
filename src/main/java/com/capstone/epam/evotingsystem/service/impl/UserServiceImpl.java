package com.capstone.epam.evotingsystem.service.impl;

import com.capstone.epam.evotingsystem.dao.RoleDao;
import com.capstone.epam.evotingsystem.dao.UserDao;
import com.capstone.epam.evotingsystem.entity.Role;
import com.capstone.epam.evotingsystem.entity.User;
import com.capstone.epam.evotingsystem.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public User createUser(String username, String password, Integer age, String userRole) {
        String encodedPassword = passwordEncoder.encode(password);
        User savedUser = userDao.save(new User(username, encodedPassword, age));
        Role roleByRoleName = roleDao.findByRoleName(userRole);
        assignRoleToUser(username, userRole);
        return savedUser;
    }

    @Override
    public void assignRoleToUser(String username, String roleName) {
        User user = loadUserByUsername(username);
        Role roleByRoleName = roleDao.findByRoleName(roleName);
        if (roleByRoleName == null) {
            throw new RuntimeException("Role not found for roleName: " + roleName);
        }
        user.assignRoleToUser(roleByRoleName);
    }

    @Override
    public String getUserRoleName(String username) {
        return this.roleDao.findRoleNameByUsername(username);
    }
}
