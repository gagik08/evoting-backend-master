package com.capstone.epam.evotingsystem.mapper;

import com.capstone.epam.evotingsystem.dto.UserDTO;
import com.capstone.epam.evotingsystem.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO fromUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setAge(user.getAge());
        userDTO.setUserRole(user.getUserRoles().get(0).getRoleName());
        return userDTO;
    }

    public User fromUserDTO(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}
