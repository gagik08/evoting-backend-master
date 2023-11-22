package com.capstone.epam.evotingsystem.dto;

import com.capstone.epam.evotingsystem.entity.User;

public class VoterDTO {
    private Long voterId;
    private String fullName;
    private UserDTO user;

    public Long getVoterId() {
        return voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
