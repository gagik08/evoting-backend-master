package com.capstone.epam.evotingsystem.service.impl.voting.referendum;

import com.capstone.epam.evotingsystem.dao.voting.referendum.ReferendumDao;
import com.capstone.epam.evotingsystem.dao.voting.referendum.ReferendumQuestionDao;
import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumQuestionDTO;
import com.capstone.epam.evotingsystem.entity.voting.referendum.ReferendumQuestion;
import com.capstone.epam.evotingsystem.mapper.voting.referendum.ReferendumMapper;
import com.capstone.epam.evotingsystem.service.voting.referendum.ReferendumQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReferendumQuestionServiceImpl implements ReferendumQuestionService {

    private ReferendumQuestionDao referendumQuestionDao;
    private ReferendumDao referendumDao;

    private ReferendumMapper referendumMapper;

    public ReferendumQuestionServiceImpl(ReferendumQuestionDao referendumQuestionDao, ReferendumDao referendumDao, ReferendumMapper referendumMapper) {
        this.referendumQuestionDao = referendumQuestionDao;
        this.referendumDao = referendumDao;
        this.referendumMapper = referendumMapper;
    }

    @Override
    public ReferendumQuestion createReferendumQuestion(ReferendumQuestionDTO referendumQuestionDTO) {
        ReferendumQuestion loadedReferendumQuestion = referendumMapper.fromReferendumQuestionDTO(referendumQuestionDTO);
        return referendumQuestionDao.save(loadedReferendumQuestion);
    }
}
