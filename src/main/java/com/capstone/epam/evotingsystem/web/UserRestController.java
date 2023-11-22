package com.capstone.epam.evotingsystem.web;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.capstone.epam.evotingsystem.dto.UserDTO;
import com.capstone.epam.evotingsystem.entity.Role;
import com.capstone.epam.evotingsystem.entity.User;
import com.capstone.epam.evotingsystem.helper.JWTHelper;
import com.capstone.epam.evotingsystem.mapper.UserMapper;
import com.capstone.epam.evotingsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.stream.Collectors;

import static com.capstone.epam.evotingsystem.constant.JWTUtil.AUTH_HEADER;
import static com.capstone.epam.evotingsystem.constant.JWTUtil.SECRET_KEY;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserRestController {

    private UserService userService;
    private JWTHelper jwtHelper;
    private UserMapper userMapper;

    public UserRestController(UserService userService, JWTHelper jwtHelper, UserMapper userMapper) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
        this.userMapper = userMapper;
    }

    @GetMapping("/check-if-exists")
//    @PreAuthorize("hasAnyAuthority('Commission Head')")
    public boolean checkIfUsernameExists(@RequestParam(name = "username", defaultValue = "") String username) {
        return userService.loadUserByUsername(username) != null;
    }

    @PostMapping("/register")
    public User createUser(@RequestBody UserDTO userDTO) throws Exception {
        User createdUser = userMapper.fromUserDTO(userDTO);
        return userService.createUser(createdUser.getUsername(), createdUser.getPassword(), createdUser.getAge(), userDTO.getUserRole());

    }

    @GetMapping("/role-name")
    public String getUserRole(@RequestParam(name = "username") String username){
        return userService.getUserRoleName(username);
    }


    @GetMapping("/refresh-token")
    public void generateNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jwtRefreshToken = jwtHelper.extractTokenFromHeaderIfExists(request.getHeader(AUTH_HEADER));

        if (jwtRefreshToken != null) {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtRefreshToken);
            String username = decodedJWT.getSubject();
            User user = userService.loadUserByUsername(username);
            String jwtAccessToken = jwtHelper.generateAccessToken(user.getUsername(), user.getUserRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), jwtHelper.getTokensMap(jwtAccessToken, jwtRefreshToken));
        } else {
            throw new RuntimeException("Refresh token required");
        }


    }
}
