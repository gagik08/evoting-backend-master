package com.capstone.epam.evotingsystem.dto.voting;

public class CategoryDTO {
    private Long categoryId;
    private String categoryTitle;
    private String categoryDescription;

    public CategoryDTO() {
    }


    public CategoryDTO(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
