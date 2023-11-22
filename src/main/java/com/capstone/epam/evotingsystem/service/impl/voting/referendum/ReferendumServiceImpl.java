package com.capstone.epam.evotingsystem.service.impl.voting.referendum;

import com.capstone.epam.evotingsystem.dao.PublisherDao;
import com.capstone.epam.evotingsystem.dao.UserDao;
import com.capstone.epam.evotingsystem.dao.VoterDao;
import com.capstone.epam.evotingsystem.dao.voting.CategoryDao;
import com.capstone.epam.evotingsystem.dao.voting.referendum.ReferendumDao;
import com.capstone.epam.evotingsystem.dao.voting.referendum.ReferendumQuestionDao;
import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumDTO;
import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumQuestionDTO;
import com.capstone.epam.evotingsystem.entity.Publisher;
import com.capstone.epam.evotingsystem.entity.Voter;
import com.capstone.epam.evotingsystem.entity.voting.Category;
import com.capstone.epam.evotingsystem.entity.voting.referendum.Referendum;
import com.capstone.epam.evotingsystem.entity.voting.referendum.ReferendumQuestion;
import com.capstone.epam.evotingsystem.mapper.PublisherMapper;
import com.capstone.epam.evotingsystem.mapper.UserMapper;
import com.capstone.epam.evotingsystem.mapper.voting.CategoryMapper;
import com.capstone.epam.evotingsystem.mapper.voting.referendum.ReferendumMapper;
import com.capstone.epam.evotingsystem.service.voting.referendum.ReferendumQuestionService;
import com.capstone.epam.evotingsystem.service.voting.referendum.ReferendumService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReferendumServiceImpl implements ReferendumService {

    private ReferendumDao referendumDao;
    private ReferendumQuestionDao referendumQuestionDao;
    private ReferendumQuestionService referendumQuestionService;
    private ReferendumMapper referendumMapper;
    private CategoryMapper categoryMapper;
    private PublisherMapper publisherMapper;
    private PublisherDao publisherDao;
    private VoterDao voterDao;
    private CategoryDao categoryDao;
    private UserMapper userMapper;
    private UserDao userDao;

    public ReferendumServiceImpl(ReferendumDao referendumDao, ReferendumQuestionService referendumQuestionService, ReferendumMapper referendumMapper, CategoryMapper categoryMapper, PublisherMapper publisherMapper, PublisherDao publisherDao, VoterDao voterDao, CategoryDao categoryDao, ReferendumQuestionDao referendumQuestionDao, UserMapper userMapper, UserDao userDao) {
        this.referendumDao = referendumDao;
        this.referendumQuestionService = referendumQuestionService;
        this.referendumMapper = referendumMapper;
        this.categoryMapper = categoryMapper;
        this.publisherMapper = publisherMapper;
        this.publisherDao = publisherDao;
        this.voterDao = voterDao;
        this.categoryDao = categoryDao;
        this.referendumQuestionDao = referendumQuestionDao;
        this.userMapper = userMapper;
        this.userDao = userDao;
    }

    @Override
    public ReferendumDTO loadReferendumById(Long referendumId) {
        Referendum referendumByReferendumId = referendumDao.findReferendumByReferendumId(referendumId);
        return referendumMapper.fromReferendum(referendumByReferendumId);
    }

    @Override
    public ReferendumDTO createReferendum(ReferendumDTO referendumDTO) {
        if (Objects.equals(referendumDTO.getTitle(), referendumDao.findReferendumByDescription(referendumDTO.getTitle()))) {
            throw new RuntimeException("Referendum with such title already exists");
        }
        Referendum referendum = referendumMapper.fromReferendumDTO(referendumDTO);

        Category loadedCategory = categoryDao.findCategoryByCategoryTitle("Referendum");
        referendum.setCategory(loadedCategory);

        ReferendumQuestion referendumQuestion = referendumMapper.fromReferendumQuestionDTO(referendumDTO.getReferendumQuestion());
        referendumQuestionDao.save(referendumQuestion);
        referendum.setReferendumQuestion(referendumQuestion);


        Publisher loadedPublisher = publisherDao.findPublisherByUsername(referendumDTO.getPublisher().getUser().getUsername());
        referendum.setPublisher(loadedPublisher);

        Referendum savedReferendum = referendumDao.save(referendum);

        return referendumMapper.fromReferendum(savedReferendum);
    }

    @Override
    public void addReferendumQuestionToReferendum(ReferendumQuestionDTO referendumQuestion, Long referendumId) {
        Referendum loadedReferendum = referendumDao.findReferendumByReferendumId(referendumId);
        ReferendumQuestion createdReferendumQuestion = referendumQuestionService.createReferendumQuestion(referendumQuestion);
        loadedReferendum.addReferendumQuestionToReferendum(createdReferendumQuestion);
        updateReferendum(referendumMapper.fromReferendum(loadedReferendum));
    }

    @Override
    public void incrementOptionValue(Long referendumId, String option) {
        Referendum loadedReferendum = referendumDao.findReferendumByReferendumId(referendumId);

        if (loadedReferendum != null && loadedReferendum.getReferendumQuestion() != null) {
            if (option.equals("Yes")){
                loadedReferendum.getReferendumQuestion().setVotesFor(loadedReferendum.getReferendumQuestion().getVotesFor() + 1);
            }
            else if (option.equals("No")){
                loadedReferendum.getReferendumQuestion().setVotesAgainst(loadedReferendum.getReferendumQuestion().getVotesAgainst() + 1);
            }

            referendumDao.save(loadedReferendum);

        }
    }

    @Override
    public ReferendumDTO updateReferendum(ReferendumDTO referendumDTO) {

        Referendum loadedReferendum = referendumDao.findReferendumByReferendumId(referendumDTO.getReferendumId());
        Publisher publisher = publisherDao.findById(referendumDTO.getPublisher().getPublisherId()).orElseThrow(() -> new EntityNotFoundException("Referendum with ID " + referendumDTO.getPublisher().getPublisherId() + " not found!"));
        Referendum referendum = referendumMapper.fromReferendumDTO(referendumDTO);
        referendum.setPublisher(publisher);
        referendum.setVoters(loadedReferendum.getVoters());
        Referendum updatedReferendum = referendumDao.save(referendum);
        return referendumMapper.fromReferendum(updatedReferendum);

    }

    @Override
    public Page<ReferendumDTO> findReferendumByTitle(String title, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Referendum> referendumsPage = referendumDao.findReferendumByTitleContains(title, pageRequest);
        return new PageImpl<>(referendumsPage.getContent().stream().map(referendum -> referendumMapper.fromReferendum(referendum)).collect(Collectors.toList()), pageRequest, referendumsPage.getTotalElements());

    }

    @Override
    public void assignVoterToReferendum(Long referendumId, Long voterId) {
        Voter voter = voterDao.findById(voterId).orElseThrow(() -> new EntityNotFoundException("Voter with ID " + voterId + " not found!"));
        Referendum referendum = referendumDao.findReferendumByReferendumId(referendumId);
        referendum.assignVoterToReferendum(voter);
    }

    @Override
    public Page<ReferendumDTO> fetchReferendumsForVoter(Long voterId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Referendum> voterReferendumsPage = referendumDao.findReferendumsByVoterId(voterId, pageRequest);
        return new PageImpl<>(voterReferendumsPage.getContent().stream().map(referendum -> referendumMapper.fromReferendum(referendum)).collect(Collectors.toList()), pageRequest, voterReferendumsPage.getTotalElements());

    }

    @Override
    public Page<ReferendumDTO> fetchReferendumsForNonSubscribedOnVoter(Long voterId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Referendum> voterNonSubscribedOnReferendumsPage = referendumDao.findNonSubscribedOnReferendumsByVoterId(voterId, pageRequest);
        return new PageImpl<>(voterNonSubscribedOnReferendumsPage.getContent().stream().map(referendum -> referendumMapper.fromReferendum(referendum)).collect(Collectors.toList()), pageRequest, voterNonSubscribedOnReferendumsPage.getTotalElements());

    }

    @Override
    public void removeReferendum(Long referendumId) {
        ReferendumQuestion referendumQuestionByReferendumId = referendumDao.findReferendumQuestionByReferendumId(referendumId);
        referendumQuestionDao.deleteById(referendumQuestionByReferendumId.getReferendumQuestionId());
        referendumDao.deleteById(referendumId);
    }

    @Override
    public Page<ReferendumDTO> fetchReferendumsForPublisher(Long publisherId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Referendum> publishersReferendumsPage = referendumDao.findReferendumsByPublisherId(publisherId, pageRequest);
        return new PageImpl<>(publishersReferendumsPage.getContent().stream().map(referendum -> referendumMapper.fromReferendum(referendum)).collect(Collectors.toList()), pageRequest, publishersReferendumsPage.getTotalElements());

    }
}
