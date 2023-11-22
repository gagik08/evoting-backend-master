package com.capstone.epam.evotingsystem.dto.voting.referendum;

import com.capstone.epam.evotingsystem.dto.PublisherDTO;
import com.capstone.epam.evotingsystem.dto.voting.CategoryDTO;
import com.capstone.epam.evotingsystem.entity.voting.Category;

public class ReferendumDTO {
    private Long referendumId;
    private String title;
    private String description;
    private boolean isActive;
    private PublisherDTO publisher;
    private CategoryDTO category;
    private ReferendumQuestionDTO referendumQuestion;

    public Long getReferendumId() {
        return referendumId;
    }

    public void setReferendumId(Long referendumId) {
        this.referendumId = referendumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public ReferendumQuestionDTO getReferendumQuestion() {
        return referendumQuestion;
    }

    public void setReferendumQuestion(ReferendumQuestionDTO referendumQuestion) {
        this.referendumQuestion = referendumQuestion;
    }
}
