package com.capstone.epam.evotingsystem.mapper.voting;

import com.capstone.epam.evotingsystem.dto.voting.CategoryDTO;
import com.capstone.epam.evotingsystem.entity.voting.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO fromCategory(Category category){
        if (category != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(category, categoryDTO);
            return categoryDTO;
        }
        return null;
    }


    public Category fromCategoryDTO(CategoryDTO categoryDTO){
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        return category;
    }
}
