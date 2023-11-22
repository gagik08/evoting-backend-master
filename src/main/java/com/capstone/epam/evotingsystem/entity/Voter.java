package com.capstone.epam.evotingsystem.entity;

import com.capstone.epam.evotingsystem.entity.voting.referendum.Referendum;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "voters")
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voter_id")
    private Long voterId;


    @Basic
    @Column(name = "full_name", nullable = false)
    private String fullName;


    @ManyToMany(mappedBy = "voters", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SociologicalSurvey> sociologicalSurveys = new ArrayList<>();

    @ManyToMany(mappedBy = "voters", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Referendum> referendums = new ArrayList<>();

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return Objects.equals(voterId, voter.voterId) && Objects.equals(fullName, voter.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voterId, fullName);
    }

    public Voter() {
    }

    public Voter(String fullName, User user) {
        this.fullName = fullName;
        this.user = user;
    }

    public Long getVoterId() {
        return voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public List<SociologicalSurvey> getSociologicalSurveys() {
        return sociologicalSurveys;
    }

    public void setSociologicalSurveys(List<SociologicalSurvey> sociologicalSurveys) {
        this.sociologicalSurveys = sociologicalSurveys;
    }

    public List<Referendum> getReferendums() {
        return referendums;
    }

    public void setReferendums(List<Referendum> referendums) {
        this.referendums = referendums;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
