package com.capstone.epam.evotingsystem.dto.voting.survey;

import com.capstone.epam.evotingsystem.dto.PublisherDTO;
import com.capstone.epam.evotingsystem.dto.voting.CategoryDTO;

import java.util.List;
import java.util.Set;

public class SociologicalSurveyDTO {
    private Long sociologicalSurveyId;
    private String title;
    private String description;
    private boolean isActive;
    private PublisherDTO publisher;
    private CategoryDTO category;
    private List<QuestionDTO> questions;

    public Long getSociologicalSurveyId() {
        return sociologicalSurveyId;
    }

    public void setSociologicalSurveyId(Long sociologicalSurveyId) {
        this.sociologicalSurveyId = sociologicalSurveyId;
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

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
