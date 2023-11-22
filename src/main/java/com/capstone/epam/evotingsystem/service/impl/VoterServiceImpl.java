package com.capstone.epam.evotingsystem.service.impl;

import com.capstone.epam.evotingsystem.dao.VoterDao;
import com.capstone.epam.evotingsystem.dao.voting.referendum.ReferendumDao;
import com.capstone.epam.evotingsystem.dao.voting.survey.SociologicalSurveyDao;
import com.capstone.epam.evotingsystem.dto.VoterDTO;
import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumDTO;
import com.capstone.epam.evotingsystem.dto.voting.survey.SociologicalSurveyDTO;
import com.capstone.epam.evotingsystem.entity.User;
import com.capstone.epam.evotingsystem.entity.Voter;
import com.capstone.epam.evotingsystem.entity.voting.referendum.Referendum;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import com.capstone.epam.evotingsystem.mapper.VoterMapper;
import com.capstone.epam.evotingsystem.mapper.voting.referendum.ReferendumMapper;
import com.capstone.epam.evotingsystem.mapper.voting.survey.SociologicalSurveyMapper;
import com.capstone.epam.evotingsystem.service.UserService;
import com.capstone.epam.evotingsystem.service.VoterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class VoterServiceImpl implements VoterService {

    private VoterDao voterDao;
    private VoterMapper voterMapper;
    private UserService userService;
    private ReferendumDao referendumDao;
    private SociologicalSurveyDao sociologicalSurveyDao;
    private ReferendumMapper referendumMapper;
    private SociologicalSurveyMapper sociologicalSurveyMapper;

    public VoterServiceImpl(VoterDao voterDao, VoterMapper voterMapper, UserService userService, ReferendumDao referendumDao, SociologicalSurveyDao sociologicalSurveyDao, ReferendumMapper referendumMapper, SociologicalSurveyMapper sociologicalSurveyMapper) {
        this.voterDao = voterDao;
        this.voterMapper = voterMapper;
        this.userService = userService;
        this.referendumDao = referendumDao;
        this.sociologicalSurveyDao = sociologicalSurveyDao;
        this.referendumMapper = referendumMapper;
        this.sociologicalSurveyMapper = sociologicalSurveyMapper;
    }

    @Override
    public Voter loadVoterById(Long voterId) {
        return voterDao.findById(voterId).orElseThrow(() -> new EntityNotFoundException("Voter with ID " + voterId + " not found!"));
    }

    @Override
    public Page<VoterDTO> loadVotersByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Voter> votersPage = voterDao.findVotersByName(name, pageRequest);
        return new PageImpl<>(votersPage.getContent().stream().map(voter -> voterMapper.fromVoter(voter)).collect(Collectors.toList()), pageRequest, votersPage.getTotalElements());
    }

    @Override
    public VoterDTO loadVoterByUsername(String username) {
        return voterMapper.fromVoter(voterDao.findVoterByUsername(username));
    }

    @Override
    public VoterDTO loadVoterByFullName(String name) {
        return voterMapper.fromVoter(voterDao.findVoterByFullName(name));
    }

    @Override
    public VoterDTO createVoter(VoterDTO voterDTO) {
        User user = userService.loadUserByUsername(voterDTO.getUser().getUsername());
        if (user == null) {
            user = userService.createUser(voterDTO.getUser().getUsername(), voterDTO.getUser().getPassword(), voterDTO.getUser().getAge(), "Voter");
        }
        Voter voter = voterMapper.fromVoterDTO(voterDTO);
        voter.setUser(user);
        voterDao.save(voter);
        return voterMapper.fromVoter(voter);
    }

    @Override
    public VoterDTO updateVoter(VoterDTO voterDTO) {
        Voter loadedVoter = loadVoterById(voterDTO.getVoterId());
        Voter voter = voterMapper.fromVoterDTO(voterDTO);
        voter.setUser(loadedVoter.getUser());
        voter.setSociologicalSurveys(loadedVoter.getSociologicalSurveys());
        Voter savedVoter = voterDao.save(voter);
        return voterMapper.fromVoter(savedVoter);
    }

    @Override
    public void removeVoter(Long voterId) {
        Voter voter = loadVoterById(voterId);
        Iterator<SociologicalSurvey> sociologicalSurveyIterator = voter.getSociologicalSurveys().iterator();
        if (sociologicalSurveyIterator.hasNext()){
            SociologicalSurvey sociologicalSurvey = sociologicalSurveyIterator.next();
            sociologicalSurvey.removeVoterFromSociologicalSurvey(voter);
        }
        voterDao.deleteById(voterId);
    }

    @Override
    public Page<Object> fetchSubscriptionsByVoterId(Long voterId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        // Fetch referendums and sociological surveys
        Page<Referendum> referendumsPage = referendumDao.findReferendumsByVoterId(voterId, pageRequest);
        Page<SociologicalSurvey> sociologicalSurveyPage = sociologicalSurveyDao.findSociologicalSurveysByVoterId(voterId, pageRequest);

        List<ReferendumDTO> referendumDTOs = referendumsPage.getContent().stream()
                .map(referendum -> referendumMapper.fromReferendum(referendum))
                .toList();

        List<SociologicalSurveyDTO> sociologicalSurveyDTOs = sociologicalSurveyPage.getContent().stream()
                .map(sociologicalSurvey -> sociologicalSurveyMapper.fromSociologicalSurvey(sociologicalSurvey))
                .toList();

        // Combine the DTOs
        List<Object> combinedDTOs = new ArrayList<>(referendumDTOs);
        combinedDTOs.addAll(sociologicalSurveyDTOs);

        // Shuffle the combined DTOs
        Collections.shuffle(combinedDTOs);

        // Calculate the start and end indices for the current page
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), combinedDTOs.size());

        // Ensure that the start index is within bounds
        if (start >= combinedDTOs.size()) {
            return new PageImpl<>(Collections.emptyList(), pageRequest, 0);
        }

        // Create a sublist representing the current page
        List<Object> pageDTOs = combinedDTOs.subList(start, end);

        // Create a new PageImpl with the shuffled DTOs
        return new PageImpl<>(pageDTOs, pageRequest, combinedDTOs.size());
    }
}
