package com.capstone.epam.evotingsystem.dto;

public class PublisherDTO {
    private Long publisherId;
    private String publicName;
    private String founder;
    private UserDTO user;

    public Long getPublisherId() {
        return publisherId;
    }


    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
