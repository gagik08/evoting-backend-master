package com.capstone.epam.evotingsystem.dao;

import com.capstone.epam.evotingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
