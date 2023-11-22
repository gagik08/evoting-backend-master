package com.capstone.epam.evotingsystem.service.impl;

import com.capstone.epam.evotingsystem.dao.PublisherDao;
import com.capstone.epam.evotingsystem.dao.voting.referendum.ReferendumDao;
import com.capstone.epam.evotingsystem.dao.voting.survey.SociologicalSurveyDao;
import com.capstone.epam.evotingsystem.dto.PublisherDTO;
import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumDTO;
import com.capstone.epam.evotingsystem.dto.voting.survey.SociologicalSurveyDTO;
import com.capstone.epam.evotingsystem.entity.Publisher;
import com.capstone.epam.evotingsystem.entity.User;
import com.capstone.epam.evotingsystem.entity.voting.referendum.Referendum;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import com.capstone.epam.evotingsystem.mapper.PublisherMapper;
import com.capstone.epam.evotingsystem.mapper.voting.referendum.ReferendumMapper;
import com.capstone.epam.evotingsystem.mapper.voting.survey.SociologicalSurveyMapper;
import com.capstone.epam.evotingsystem.service.PublisherService;
import com.capstone.epam.evotingsystem.service.UserService;
import com.capstone.epam.evotingsystem.service.voting.survey.SociologicalSurveyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {

    private PublisherDao publisherDao;
    private PublisherMapper publisherMapper;
    private UserService userService;

    private SociologicalSurveyDao sociologicalSurveyDao;
    private SociologicalSurveyService sociologicalSurveyService;
    private ReferendumDao referendumDao;
    private ReferendumMapper referendumMapper;
    private SociologicalSurveyMapper sociologicalSurveyMapper;

    public PublisherServiceImpl(PublisherDao publisherDao, PublisherMapper publisherMapper, UserService userService, SociologicalSurveyDao sociologicalSurveyDao, SociologicalSurveyService sociologicalSurveyService, ReferendumDao referendumDao, ReferendumMapper referendumMapper, SociologicalSurveyMapper sociologicalSurveyMapper) {
        this.publisherDao = publisherDao;
        this.publisherMapper = publisherMapper;
        this.userService = userService;
        this.sociologicalSurveyDao = sociologicalSurveyDao;
        this.sociologicalSurveyService = sociologicalSurveyService;

        this.referendumDao = referendumDao;
        this.referendumMapper = referendumMapper;
        this.sociologicalSurveyMapper = sociologicalSurveyMapper;
    }

    @Override
    public Publisher loadPublisherById(Long publisherId) {
        return publisherDao.findById(publisherId).orElseThrow(() -> new EntityNotFoundException("Publisher with ID " + publisherId + " not found!"));
    }

    @Override
    public Page<PublisherDTO> findPublishersByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Publisher> publishersPage = publisherDao.findPublishersByName(name, pageRequest);
        return new PageImpl<>(publishersPage.getContent().stream().map(publisher -> publisherMapper.fromPublisher(publisher)).collect(Collectors.toList()), pageRequest, publishersPage.getTotalElements());

    }

    @Override
    public PublisherDTO loadPublisherByUsername(String username) {
        Publisher publisherByUsername = publisherDao.findPublisherByUsername(username);
        if (publisherByUsername != null) {
            return publisherMapper.fromPublisher(publisherByUsername);
        }
        throw new RuntimeException("Publisher with such username doesn't exist");
    }


    @Override
    public PublisherDTO createPublisher(PublisherDTO publisherDTO) {
        User user = userService.loadUserByUsername(publisherDTO.getUser().getUsername());
        if (user == null) {
            user = userService.createUser(publisherDTO.getUser().getUsername(), publisherDTO.getUser().getPassword(), publisherDTO.getUser().getAge(), "Publisher");
        }
        Publisher publisher = publisherMapper.fromPublisherDTO(publisherDTO);
        publisher.setUser(user);
        publisherDao.save(publisher);
        return publisherMapper.fromPublisher(publisher);
    }

    @Override
    public PublisherDTO updatePublisher(PublisherDTO publisherDTO) {
        Publisher loadedPublisher = loadPublisherById(publisherDTO.getPublisherId());
        Publisher publisher = publisherMapper.fromPublisherDTO(publisherDTO);
        publisher.setUser(loadedPublisher.getUser());
        publisher.setSociologicalSurveys(loadedPublisher.getSociologicalSurveys());
        Publisher updatedPublisher = publisherDao.save(publisher);
        return publisherMapper.fromPublisher(updatedPublisher);
    }

    @Override
    public List<PublisherDTO> fetchPublishers() {
        return publisherDao.findAll().stream().map(publisher -> publisherMapper.fromPublisher(publisher)).collect(Collectors.toList());
    }

    @Override
    public void removePublisher(Long publisherId) {
        Publisher publisher = loadPublisherById(publisherId);
        for (SociologicalSurvey sociologicalSurvey : publisher.getSociologicalSurveys()) {
            sociologicalSurveyService.removeSociologicalSurvey(sociologicalSurvey.getSociologicalSurveyId());
        }
        publisherDao.deleteById(publisherId);
    }

    @Override
    public Page<Object> fetchPublicationsByPublisherId(Long publisherId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        // Fetch referendums and sociological surveys
        Page<Referendum> referendumsPage = referendumDao.findReferendumsByPublisherId(publisherId, pageRequest);
        Page<SociologicalSurvey> sociologicalSurveyPage = sociologicalSurveyDao.findSociologicalSurveysByPublisherId(publisherId, pageRequest);

        List<ReferendumDTO> referendumDTOs = referendumsPage.getContent().stream()
                .map(referendum -> referendumMapper.fromReferendum(referendum))
                .toList();

        List<SociologicalSurveyDTO> sociologicalSurveyDTOs = sociologicalSurveyPage.getContent().stream()
                .map(sociologicalSurvey -> sociologicalSurveyMapper.fromSociologicalSurvey(sociologicalSurvey))
                .toList();

        List<Object> combinedDTOs = new ArrayList<>(referendumDTOs);
        combinedDTOs.addAll(sociologicalSurveyDTOs);

        Collections.shuffle(combinedDTOs);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), combinedDTOs.size());

        if (start >= combinedDTOs.size()) {
            return new PageImpl<>(Collections.emptyList(), pageRequest, 0);
        }

        List<Object> pageDTOs = combinedDTOs.subList(start, end);

        return new PageImpl<>(pageDTOs, pageRequest, combinedDTOs.size());
    }
}
