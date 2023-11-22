package com.capstone.epam.evotingsystem.entity.voting.survey;

import jakarta.persistence.*;

import java.util.Map;
import java.util.Objects;


@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Basic
    @Column(name = "question_content")
    private String content;

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @MapKeyColumn(name = "option_key")
    @Column(name = "option")
    private Map<String, Integer> options;

    @ManyToOne(fetch = FetchType.EAGER)
    private SociologicalSurvey sociologicalSurvey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(questionId, question.questionId) && Objects.equals(content, question.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, content);
    }

    public Question() {
    }

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

    public SociologicalSurvey getSociologicalSurvey() {
        return sociologicalSurvey;
    }

    public void setSociologicalSurvey(SociologicalSurvey sociologicalSurvey) {
        this.sociologicalSurvey = sociologicalSurvey;
    }


}
