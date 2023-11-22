package com.capstone.epam.evotingsystem.service.voting.referendum;

import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumDTO;
import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumQuestionDTO;
import org.springframework.data.domain.Page;

public interface ReferendumService {
    ReferendumDTO loadReferendumById(Long referendumId);
    ReferendumDTO createReferendum(ReferendumDTO referendumDTO);
    ReferendumDTO updateReferendum(ReferendumDTO referendumDTO);
    Page<ReferendumDTO> findReferendumByTitle(String title, int page, int size);
    void assignVoterToReferendum(Long referendumId, Long voterId);
    Page<ReferendumDTO> fetchReferendumsForVoter(Long voterId, int page, int size);
    Page<ReferendumDTO> fetchReferendumsForNonSubscribedOnVoter(Long voterId, int page, int size);
    void removeReferendum(Long referendumId);
    Page<ReferendumDTO> fetchReferendumsForPublisher(Long publisherId, int page, int size);
    void addReferendumQuestionToReferendum(ReferendumQuestionDTO referendumQuestion, Long referendumId);

    void incrementOptionValue(Long referendumId, String option);
}
