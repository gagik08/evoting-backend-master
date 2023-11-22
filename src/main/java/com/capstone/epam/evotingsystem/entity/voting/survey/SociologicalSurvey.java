package com.capstone.epam.evotingsystem.entity.voting.survey;

import com.capstone.epam.evotingsystem.entity.Publisher;
import com.capstone.epam.evotingsystem.entity.Voter;
import com.capstone.epam.evotingsystem.entity.voting.Category;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "sociological_surveys")
public class SociologicalSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sociological_survey_id")
    private Long sociologicalSurveyId;
    @Basic
    @Column(name = "sociological_survey_title")
    private String title;
    @Basic
    @Column(name = "sociological_survey_description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id")
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "sociological_survey_voters",
            joinColumns = {@JoinColumn(name = "sociological_survey_id")},
            inverseJoinColumns = {@JoinColumn(name = "voter_id")})
    private List<Voter> voters = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "sociologicalSurvey", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    public void assignVoterToSociologicalSurvey(Voter voter) {
        this.voters.add(voter);
        voter.getSociologicalSurveys().add(this);
    }

    public void removeVoterFromSociologicalSurvey(Voter voter) {
        this.voters.remove(voter);
        voter.getSociologicalSurveys().remove(this);
    }
    public void addQuestionToSociologicalSurvey(Question question){
        this.questions.add(question);
    }

    public void removeQuestionFromSociologicalSurvey(Question question){
        this.questions.remove(question);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SociologicalSurvey that = (SociologicalSurvey) o;
        return Objects.equals(sociologicalSurveyId, that.sociologicalSurveyId) && Objects.equals(title, that.title) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sociologicalSurveyId, title, description);
    }

    public SociologicalSurvey() {
    }

    public SociologicalSurvey(String title, String description) {
        this.title = title;
        this.description = description;
    }

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


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Voter> getVoters() {
        return voters;
    }

    public void setVoters(List<Voter> voters) {
        this.voters = voters;
    }


}
