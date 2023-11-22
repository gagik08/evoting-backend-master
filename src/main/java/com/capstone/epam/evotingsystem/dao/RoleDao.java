package com.capstone.epam.evotingsystem.dao;

import com.capstone.epam.evotingsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleDao extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM roles AS r WHERE role_name = :roleName", nativeQuery = true)
    Role findByRoleName(String roleName);

    @Query("SELECT r.roleName FROM Role r JOIN r.users u WHERE u.username = :username")
    String findRoleNameByUsername(@Param("username") String username);

}
