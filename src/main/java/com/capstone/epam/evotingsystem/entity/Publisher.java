package com.capstone.epam.evotingsystem.entity;

import com.capstone.epam.evotingsystem.entity.voting.referendum.Referendum;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long publisherId;
    @Basic
    @Column(name = "publisher_public_name")
    private String publicName;

    @Basic
    @Column(name = "founder")
    private String founder;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SociologicalSurvey> sociologicalSurveys = new ArrayList<>();

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Referendum> referendums = new ArrayList<>();

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(publisherId, publisher.publisherId) && Objects.equals(publicName, publisher.publicName) && Objects.equals(founder, publisher.founder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publisherId, publicName, founder);
    }

    public Publisher() {
    }

    public Publisher(String publicName, String founder, User user) {
        this.publicName = publicName;
        this.founder = founder;
        this.user = user;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
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
}
