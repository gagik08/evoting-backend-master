package com.capstone.epam.evotingsystem.dto.voting.referendum;

public class ReferendumQuestionDTO {
    private Long referendumQuestionId;
    private String optionFor;
    private String optionAgainst;
    private Integer votesFor = 0;
    private Integer votesAgainst = 0;

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
}
