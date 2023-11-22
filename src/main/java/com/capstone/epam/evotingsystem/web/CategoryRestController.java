package com.capstone.epam.evotingsystem.web;

import com.capstone.epam.evotingsystem.dto.voting.CategoryDTO;
import com.capstone.epam.evotingsystem.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Transactional
@RequestMapping("categories")
public class CategoryRestController {
    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    List<CategoryDTO> findAllCategories(){
        return this.categoryService.fetchCategories();
    }

}
