package com.capstone.epam.evotingsystem.mapper.voting.referendum;

import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumDTO;
import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumQuestionDTO;
import com.capstone.epam.evotingsystem.entity.voting.referendum.Referendum;
import com.capstone.epam.evotingsystem.entity.voting.referendum.ReferendumQuestion;
import com.capstone.epam.evotingsystem.mapper.PublisherMapper;
import com.capstone.epam.evotingsystem.mapper.voting.CategoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ReferendumMapper {

    private PublisherMapper publisherMapper;
    private CategoryMapper categoryMapper;

    public ReferendumMapper(PublisherMapper publisherMapper, CategoryMapper categoryMapper) {
        this.publisherMapper = publisherMapper;
        this.categoryMapper = categoryMapper;
    }

    public ReferendumDTO fromReferendum(Referendum referendum){
        ReferendumDTO referendumDTO = new ReferendumDTO();
        BeanUtils.copyProperties(referendum,referendumDTO);
        referendumDTO.setPublisher(publisherMapper.fromPublisher(referendum.getPublisher()));
        referendumDTO.setCategory(categoryMapper.fromCategory(referendum.getCategory()));
        referendumDTO.setReferendumQuestion(fromReferendumQuestion(referendum.getReferendumQuestion()));
        return referendumDTO;
    }

    public Referendum fromReferendumDTO(ReferendumDTO referendumDTO) {
        Referendum referendum = new Referendum();
        BeanUtils.copyProperties(referendumDTO,referendum);
        referendum.setReferendumQuestion(fromReferendumQuestionDTO(referendumDTO.getReferendumQuestion()));
        referendum.setPublisher(publisherMapper.fromPublisherDTO(referendumDTO.getPublisher()));
        referendum.setCategory(categoryMapper.fromCategoryDTO(referendumDTO.getCategory()));
        return referendum;
    }

    public ReferendumQuestionDTO fromReferendumQuestion(ReferendumQuestion referendumQuestion){
        ReferendumQuestionDTO referendumQuestionDTO = new ReferendumQuestionDTO();
        BeanUtils.copyProperties(referendumQuestion, referendumQuestionDTO);
        return referendumQuestionDTO;
    }

    public ReferendumQuestion fromReferendumQuestionDTO(ReferendumQuestionDTO referendumQuestionDTO){
        ReferendumQuestion referendumQuestion = new ReferendumQuestion();
        BeanUtils.copyProperties(referendumQuestionDTO, referendumQuestion);
        referendumQuestion.setReferendumQuestionId(referendumQuestion.getReferendumQuestionId());
        referendumQuestion.setOptionFor(referendumQuestionDTO.getOptionFor());
        referendumQuestion.setOptionAgainst(referendumQuestionDTO.getOptionAgainst());
        referendumQuestion.setVotesFor(referendumQuestionDTO.getVotesFor());
        referendumQuestion.setVotesAgainst(referendumQuestionDTO.getVotesAgainst());
        return referendumQuestion;
    }
}
