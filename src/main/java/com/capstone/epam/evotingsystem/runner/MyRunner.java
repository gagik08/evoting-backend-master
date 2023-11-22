package com.capstone.epam.evotingsystem.runner;

import com.capstone.epam.evotingsystem.dto.voting.CategoryDTO;
import com.capstone.epam.evotingsystem.entity.User;
import com.capstone.epam.evotingsystem.mapper.UserMapper;
import com.capstone.epam.evotingsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VoterService voterService;



    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createCategories();
        createAdmin();
    }


    private void createAdmin() {
        User createdUser = userService.createUser("commission_member", "adminpassword", 19, "Commission Member");
    }

    private void createCategories() {
        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        CategoryDTO sociologicalSurveyDTO = new CategoryDTO();
        sociologicalSurveyDTO.setCategoryTitle("Sociological Survey");
        sociologicalSurveyDTO.setCategoryDescription("A survey is a research method sociologists use to gather data from respondents by asking a series of questions about their opinions or human behaviour.");

        CategoryDTO referendumDTO = new CategoryDTO();
        referendumDTO.setCategoryTitle("Referendum");
        referendumDTO.setCategoryDescription("A referendum is an official vote on a specific issue." +
                " It's often part of a larger election.");


        categoryDTOs.add(sociologicalSurveyDTO);
        categoryDTOs.add(referendumDTO);

        categoryDTOs.forEach(categoryDTO -> categoryService.createCategory(categoryDTO));
    }

    private void createRoles() {
        Arrays.asList("Commission Member", "Publisher", "Voter").forEach(role -> roleService.createRole(role));
    }
}
