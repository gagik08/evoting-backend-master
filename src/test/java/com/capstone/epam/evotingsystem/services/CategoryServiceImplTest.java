package com.capstone.epam.evotingsystem.services;

import com.capstone.epam.evotingsystem.dao.voting.CategoryDao;
import com.capstone.epam.evotingsystem.dto.voting.CategoryDTO;
import com.capstone.epam.evotingsystem.entity.voting.Category;
import com.capstone.epam.evotingsystem.mapper.voting.CategoryMapper;
import com.capstone.epam.evotingsystem.service.impl.voting.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void testCreateCategory() {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryTitle("TestCategory");
        categoryDTO.setCategoryDescription("TestDescription");

        when(categoryDao.save(any())).thenReturn(new Category());

        Category createdCategory = categoryService.createCategory(categoryDTO);

        assertNotNull(createdCategory);

        verify(categoryDao, times(1)).save(any());
    }


    @Test
    public void testFetchCategories() {
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(new Category());
        mockCategories.add(new Category());

        when(categoryDao.findAll()).thenReturn(mockCategories);

        when(categoryMapper.fromCategory(any())).thenReturn(new CategoryDTO());

        List<CategoryDTO> categories = categoryService.fetchCategories();

        assertNotNull(categories);
        assertEquals(2, categories.size());
    }
}
