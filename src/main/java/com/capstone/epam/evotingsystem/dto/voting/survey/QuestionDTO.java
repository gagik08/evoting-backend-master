package com.capstone.epam.evotingsystem.dto.voting.survey;

import java.util.Map;

public class QuestionDTO {
    private Long questionId;
    private String content;
    private Map<String, Integer> options;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Integer> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Integer> options) {
        this.options = options;
    }

}
