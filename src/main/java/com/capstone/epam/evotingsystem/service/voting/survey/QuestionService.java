package com.capstone.epam.evotingsystem.service.voting.survey;

import com.capstone.epam.evotingsystem.dto.voting.survey.QuestionDTO;
import com.capstone.epam.evotingsystem.entity.voting.survey.Question;

public interface QuestionService {
    Question createQuestion(QuestionDTO question);
}
