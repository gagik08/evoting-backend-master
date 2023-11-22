package com.capstone.epam.evotingsystem.mapper.voting.survey;

import com.capstone.epam.evotingsystem.dto.voting.survey.QuestionDTO;
import com.capstone.epam.evotingsystem.dto.voting.survey.SociologicalSurveyDTO;
import com.capstone.epam.evotingsystem.entity.voting.survey.Question;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import com.capstone.epam.evotingsystem.mapper.PublisherMapper;
import com.capstone.epam.evotingsystem.mapper.voting.CategoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;




@Component
public class SociologicalSurveyMapper {

    @Autowired
    private PublisherMapper publisherMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public SociologicalSurveyDTO fromSociologicalSurvey(SociologicalSurvey sociologicalSurvey) {
        SociologicalSurveyDTO sociologicalSurveyDTO = new SociologicalSurveyDTO();
        BeanUtils.copyProperties(sociologicalSurvey,sociologicalSurveyDTO);
        sociologicalSurveyDTO.setPublisher(publisherMapper.fromPublisher(sociologicalSurvey.getPublisher()));
        sociologicalSurveyDTO.setCategory(categoryMapper.fromCategory(sociologicalSurvey.getCategory()));
        sociologicalSurveyDTO.setQuestions(
                sociologicalSurvey.getQuestions()
                        .stream()
                        .map(this::fromQuestion)
                        .collect(Collectors.toList())
        );
        return sociologicalSurveyDTO;
    }

    public SociologicalSurvey fromSociologicalSurveyDTO(SociologicalSurveyDTO sociologicalSurveyDTO) {
        SociologicalSurvey sociologicalSurvey = new SociologicalSurvey();
        BeanUtils.copyProperties(sociologicalSurveyDTO, sociologicalSurvey);
        if (sociologicalSurveyDTO.getPublisher() != null) {
            sociologicalSurvey.setPublisher(publisherMapper.fromPublisherDTO(sociologicalSurveyDTO.getPublisher()));
        }

        if (sociologicalSurveyDTO.getCategory() != null) {
            sociologicalSurvey.setCategory(categoryMapper.fromCategoryDTO(sociologicalSurveyDTO.getCategory()));
        }
        List<Question> questions = sociologicalSurveyDTO.getQuestions()
                .stream()
                .map(this::fromQuestionDTO)
                .collect(Collectors.toList());

        for (Question question : questions) {
            question.setSociologicalSurvey(sociologicalSurvey);
        }

        sociologicalSurvey.setQuestions(questions);

        return sociologicalSurvey;
    }

    public QuestionDTO fromQuestion(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setOptions(question.getOptions());
        return questionDTO;
    }

    public Question fromQuestionDTO(QuestionDTO questionDTO) {
        Question question = new Question();
        BeanUtils.copyProperties(questionDTO, question);
        question.setContent(questionDTO.getContent());
        return question;
    }
}
