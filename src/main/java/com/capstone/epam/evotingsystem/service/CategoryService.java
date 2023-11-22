package com.capstone.epam.evotingsystem.service;

import com.capstone.epam.evotingsystem.dto.voting.CategoryDTO;
import com.capstone.epam.evotingsystem.entity.Role;
import com.capstone.epam.evotingsystem.entity.voting.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> fetchCategories();
}
