package com.capstone.epam.evotingsystem.service.impl.voting;

import com.capstone.epam.evotingsystem.dao.voting.CategoryDao;
import com.capstone.epam.evotingsystem.dto.voting.CategoryDTO;
import com.capstone.epam.evotingsystem.entity.voting.Category;
import com.capstone.epam.evotingsystem.mapper.voting.CategoryMapper;
import com.capstone.epam.evotingsystem.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao;
    private CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryDao categoryDao, CategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        // Convert CategoryDTO to Category entity
        Category category = new Category();
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());

        // Save the category using the JpaRepository method
        return categoryDao.save(category);
    }

    @Override
    public List<CategoryDTO> fetchCategories() {
        return categoryDao.findAll().stream().map(category -> categoryMapper.fromCategory(category)).collect(Collectors.toList());
    }
}
