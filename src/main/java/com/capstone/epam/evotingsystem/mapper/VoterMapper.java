package com.capstone.epam.evotingsystem.mapper;

import com.capstone.epam.evotingsystem.dto.VoterDTO;
import com.capstone.epam.evotingsystem.entity.Voter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class VoterMapper {

    public VoterDTO fromVoter(Voter voter){
        VoterDTO voterDTO = new VoterDTO();
        voterDTO.setVoterId(voter.getVoterId());
        voterDTO.setFullName(voter.getFullName());
        voterDTO.setUser(voterDTO.getUser());
        return voterDTO;
    }

    public Voter fromVoterDTO(VoterDTO voterDTO) {
        Voter voter = new Voter();
        BeanUtils.copyProperties(voterDTO, voter);
        return voter;
    }


}
