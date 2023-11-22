package com.capstone.epam.evotingsystem.entity.voting.referendum;

import com.capstone.epam.evotingsystem.entity.Publisher;
import com.capstone.epam.evotingsystem.entity.Voter;
import com.capstone.epam.evotingsystem.entity.voting.Category;
import com.capstone.epam.evotingsystem.entity.voting.survey.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "referendum")
public class Referendum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long referendumId;

    @Basic
    @Column(name = "referendum_title", unique = true)
    private String title;

    @Basic
    @Column(name = "referendum_description")
    private String description;
    @Basic
    @Column(name = "is_active")
    private boolean isActive = false;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ReferendumQuestion referendumQuestion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id", nullable = false)
    @JsonIgnore
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "referendum_voters",
            joinColumns = {@JoinColumn(name = "referendum_id")},
            inverseJoinColumns = {@JoinColumn(name = "voter_id")})
    private Set<Voter> voters = new HashSet<>();



    public void assignVoterToReferendum(Voter voter){
        this.voters.add(voter);
        voter.getReferendums().add(this);
    }

    public void removeVoterFromReferendum(Voter voter){
        this.voters.remove(voter);
        voter.getReferendums().remove(this);
    }

    public void addReferendumQuestionToReferendum(ReferendumQuestion question){
        setReferendumQuestion(question);
    }

    public void removeQuestionFromSociologicalSurvey(Question question){
        this.referendumQuestion = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Referendum that = (Referendum) o;
        return isActive == that.isActive && Objects.equals(referendumId, that.referendumId) && Objects.equals(title, that.title) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(referendumId, title, description, isActive);
    }

    public Referendum() {
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Set<Voter> getVoters() {
        return voters;
    }

    public void setVoters(Set<Voter> voters) {
        this.voters = voters;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ReferendumQuestion getReferendumQuestion() {
        return referendumQuestion;
    }

    public void setReferendumQuestion(ReferendumQuestion referendumQuestion) {
        this.referendumQuestion = referendumQuestion;
    }

}
