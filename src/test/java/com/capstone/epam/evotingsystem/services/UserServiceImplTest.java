package com.capstone.epam.evotingsystem.services;

import com.capstone.epam.evotingsystem.dao.RoleDao;
import com.capstone.epam.evotingsystem.dao.UserDao;
import com.capstone.epam.evotingsystem.entity.Role;
import com.capstone.epam.evotingsystem.entity.User;
import com.capstone.epam.evotingsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void testCreateUser(){
        User user = new User();
        Role role = new Role();
        Mockito.when(userDao.findUserByUsername(any())).thenReturn(user);
        Mockito.when(passwordEncoder.encode(any())).thenReturn("pas");
        Mockito.when(roleDao.findByRoleName(any())).thenReturn(new Role());
        Mockito.when(userDao.save(any())).thenReturn(user);
        Assertions.assertEquals(user, userService.createUser("usr", "pass", 10, "Referendum"));
    }





}
