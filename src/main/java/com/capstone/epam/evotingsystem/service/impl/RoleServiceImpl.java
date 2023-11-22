package com.capstone.epam.evotingsystem.service.impl;

import com.capstone.epam.evotingsystem.dao.RoleDao;
import com.capstone.epam.evotingsystem.entity.Role;
import com.capstone.epam.evotingsystem.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role createRole(String roleName) {
        return roleDao.save(new Role(roleName));
    }
}
