package com.capstone.epam.evotingsystem.service;

import com.capstone.epam.evotingsystem.dto.VoterDTO;
import com.capstone.epam.evotingsystem.entity.Voter;
import org.springframework.data.domain.Page;

public interface VoterService {
    Voter loadVoterById(Long voterId);
    Page<VoterDTO> loadVotersByName(String name, int page, int size);
    VoterDTO loadVoterByUsername(String username);
    VoterDTO loadVoterByFullName(String name);
    VoterDTO createVoter(VoterDTO voterDTO);
    VoterDTO updateVoter(VoterDTO voterDTO);
    void removeVoter(Long voterId);
    Page<Object> fetchSubscriptionsByVoterId(Long voterId, int page, int size);
}
