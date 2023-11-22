package com.capstone.epam.evotingsystem.service.voting.referendum;

import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumQuestionDTO;
import com.capstone.epam.evotingsystem.entity.voting.referendum.ReferendumQuestion;

public interface ReferendumQuestionService {
    ReferendumQuestion createReferendumQuestion(ReferendumQuestionDTO referendumQuestionDTO);
}
