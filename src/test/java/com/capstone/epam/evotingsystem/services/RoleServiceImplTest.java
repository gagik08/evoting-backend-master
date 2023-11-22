package com.capstone.epam.evotingsystem.services;

import com.capstone.epam.evotingsystem.dao.RoleDao;
import com.capstone.epam.evotingsystem.entity.Role;
import com.capstone.epam.evotingsystem.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {
    @Mock
    private RoleDao roleDao;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testCreateRole(){
        Role role = new Role();
        Mockito.when(roleDao.save(any())).thenReturn(role);
        Assertions.assertEquals(role, roleService.createRole("ABC"));
    }
}
