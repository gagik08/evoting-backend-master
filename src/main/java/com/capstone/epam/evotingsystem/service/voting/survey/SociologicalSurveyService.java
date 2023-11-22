package com.capstone.epam.evotingsystem.service.voting.survey;

import com.capstone.epam.evotingsystem.dto.voting.survey.QuestionDTO;
import com.capstone.epam.evotingsystem.dto.voting.survey.SociologicalSurveyDTO;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SociologicalSurveyService {
    SociologicalSurvey loadSociologicalSurveyById(Long sociologicalSurveyId);

    SociologicalSurveyDTO createSociologicalSurvey(SociologicalSurveyDTO sociologicalSurveyDTO);

    SociologicalSurveyDTO updateSociologicalSurvey(SociologicalSurveyDTO sociologicalSurveyDTO);

    Page<SociologicalSurveyDTO> findSociologicalSurveyByTitle(String keyword, int page, int size);

    void assignVoterToSociologicalSurvey(Long sociologicalSurveyId, Long voterId);

    Page<SociologicalSurveyDTO> fetchSociologicalSurveysForVoter(Long voterId, int page, int size);

    Page<SociologicalSurveyDTO> fetchSociologicalSurveysForNonSubscribedOnVoter(Long voterId, int page, int size);

    void removeSociologicalSurvey(Long sociologicalSurveyId);

    Page<SociologicalSurveyDTO> fetchSociologicalSurveysForPublisher(Long publisherId, int page, int size);

    void addQuestionToSociologicalSurvey(QuestionDTO question, Long sociologicalSurveyId);
    List<QuestionDTO> fetchQuestionsBySociologicalSurveyId(Long sociologicalSurveyId);

    void incrementOptionValue(Long sociologicalSurveyId, String optionKey);
}
