package com.capstone.epam.evotingsystem.service.impl.voting.survey;

import com.capstone.epam.evotingsystem.dao.voting.survey.QuestionDao;
import com.capstone.epam.evotingsystem.dto.voting.survey.QuestionDTO;
import com.capstone.epam.evotingsystem.entity.voting.survey.Question;
import com.capstone.epam.evotingsystem.mapper.voting.survey.SociologicalSurveyMapper;
import com.capstone.epam.evotingsystem.service.voting.survey.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    private QuestionDao questionDao;

    private SociologicalSurveyMapper sociologicalSurveyMapper;

    public QuestionServiceImpl(QuestionDao questionDao, SociologicalSurveyMapper sociologicalSurveyMapper) {
        this.questionDao = questionDao;
        this.sociologicalSurveyMapper = sociologicalSurveyMapper;
    }

    @Override
    public Question createQuestion(QuestionDTO questionDTO) {
        Question questionByContent = questionDao.findQuestionByContent(questionDTO.getContent());

        Question loadedQuestion = sociologicalSurveyMapper.fromQuestionDTO(questionDTO);
        loadedQuestion.setContent(questionDTO.getContent());
        loadedQuestion.setOptions(questionDTO.getOptions());
        if (questionByContent == null) {
            return questionDao.save(loadedQuestion);
        }
        return sociologicalSurveyMapper.fromQuestionDTO(questionDTO);
    }
}
