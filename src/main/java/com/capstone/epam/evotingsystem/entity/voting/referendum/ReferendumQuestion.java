package com.capstone.epam.evotingsystem.entity.voting.referendum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "referendum_questions")
public class ReferendumQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long referendumQuestionId;

    @Basic
    @Column(name = "option_for")
    private String optionFor;

    @Basic
    @Column(name = "option_against")
    private String optionAgainst;

    @Basic
    @Column(name = "votes_for")
    private Integer votesFor = 0;
    @Basic
    @Column(name = "votes_against")
    private Integer votesAgainst = 0;

    @OneToOne(mappedBy = "referendumQuestion", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Referendum referendumQuestion;

    public ReferendumQuestion() {
    }

    public Long getReferendumQuestionId() {
        return referendumQuestionId;
    }

    public void setReferendumQuestionId(Long referendumQuestionId) {
        this.referendumQuestionId = referendumQuestionId;
    }


    public String getOptionFor() {
        return optionFor;
    }

    public void setOptionFor(String optionFor) {
        this.optionFor = optionFor;
    }

    public String getOptionAgainst() {
        return optionAgainst;
    }

    public void setOptionAgainst(String optionAgainst) {
        this.optionAgainst = optionAgainst;
    }

    public Integer getVotesFor() {
        return votesFor;
    }

    public void setVotesFor(Integer votesFor) {
        this.votesFor = votesFor;
    }

    public Integer getVotesAgainst() {
        return votesAgainst;
    }

    public void setVotesAgainst(Integer votesAgainst) {
        this.votesAgainst = votesAgainst;
    }

    public Referendum getReferendumQuestion() {
        return referendumQuestion;
    }

    public void setReferendumQuestion(Referendum referendumQuestion) {
        this.referendumQuestion = referendumQuestion;
    }
}
