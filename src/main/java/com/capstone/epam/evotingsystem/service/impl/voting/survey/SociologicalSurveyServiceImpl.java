package com.capstone.epam.evotingsystem.service.impl.voting.survey;

import com.capstone.epam.evotingsystem.dao.PublisherDao;
import com.capstone.epam.evotingsystem.dao.UserDao;
import com.capstone.epam.evotingsystem.dao.VoterDao;
import com.capstone.epam.evotingsystem.dao.voting.CategoryDao;
import com.capstone.epam.evotingsystem.dao.voting.survey.QuestionDao;
import com.capstone.epam.evotingsystem.dao.voting.survey.SociologicalSurveyDao;
import com.capstone.epam.evotingsystem.dto.voting.survey.QuestionDTO;
import com.capstone.epam.evotingsystem.dto.voting.survey.SociologicalSurveyDTO;
import com.capstone.epam.evotingsystem.entity.Publisher;
import com.capstone.epam.evotingsystem.entity.Voter;
import com.capstone.epam.evotingsystem.entity.voting.Category;
import com.capstone.epam.evotingsystem.entity.voting.survey.Question;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import com.capstone.epam.evotingsystem.mapper.PublisherMapper;
import com.capstone.epam.evotingsystem.mapper.UserMapper;
import com.capstone.epam.evotingsystem.mapper.voting.CategoryMapper;
import com.capstone.epam.evotingsystem.mapper.voting.survey.SociologicalSurveyMapper;
import com.capstone.epam.evotingsystem.service.voting.survey.QuestionService;
import com.capstone.epam.evotingsystem.service.voting.survey.SociologicalSurveyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SociologicalSurveyServiceImpl implements SociologicalSurveyService {
    private SociologicalSurveyDao sociologicalSurveyDao;
    private QuestionDao questionDao;
    private QuestionService questionService;
    private SociologicalSurveyMapper sociologicalSurveyMapper;
    private PublisherDao publisherDao;
    private PublisherMapper publisherMapper;
    private VoterDao voterDao;
    private CategoryDao categoryDao;
    private CategoryMapper categoryMapper;
    private UserDao userDao;
    private UserMapper userMapper;

    public SociologicalSurveyServiceImpl(SociologicalSurveyDao sociologicalSurveyDao, QuestionDao questionDao, QuestionService questionService, SociologicalSurveyMapper sociologicalSurveyMapper, PublisherDao publisherDao, PublisherMapper publisherMapper, VoterDao voterDao, CategoryDao categoryDao, CategoryMapper categoryMapper, UserDao userDao, UserMapper userMapper) {
        this.sociologicalSurveyDao = sociologicalSurveyDao;
        this.questionDao = questionDao;
        this.questionService = questionService;
        this.sociologicalSurveyMapper = sociologicalSurveyMapper;
        this.publisherDao = publisherDao;
        this.publisherMapper = publisherMapper;
        this.voterDao = voterDao;
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public SociologicalSurvey loadSociologicalSurveyById(Long sociologicalSurveyId) {
        return sociologicalSurveyDao.findById(sociologicalSurveyId).orElseThrow(() -> new EntityNotFoundException("Sociological Survey with ID " + sociologicalSurveyId + " not found!"));
    }


    @Override
    public SociologicalSurveyDTO createSociologicalSurvey(SociologicalSurveyDTO sociologicalSurveyDTO) {
        SociologicalSurvey sociologicalSurvey = sociologicalSurveyMapper.fromSociologicalSurveyDTO(sociologicalSurveyDTO);

        Category loadedCategory = categoryDao.findCategoryByCategoryTitle("Sociological Survey");
        sociologicalSurvey.setCategory(loadedCategory);

        Publisher loadedPublisher = publisherDao.findPublisherByUsername(sociologicalSurveyDTO.getPublisher().getUser().getUsername());
        sociologicalSurvey.setPublisher(loadedPublisher);

        SociologicalSurvey savedSociologicalSurvey = sociologicalSurveyDao.save(sociologicalSurvey);

//        sociologicalSurveyDTO.getQuestions().forEach(question -> this.addQuestionToSociologicalSurvey(question, savedSociologicalSurvey.getSociologicalSurveyId()));

        return sociologicalSurveyMapper.fromSociologicalSurvey(savedSociologicalSurvey);
    }




    @Override
    public SociologicalSurveyDTO updateSociologicalSurvey(SociologicalSurveyDTO sociologicalSurveyDTO) {
        SociologicalSurvey loadedSociologicalSurvey = loadSociologicalSurveyById(sociologicalSurveyDTO.getSociologicalSurveyId());
        Publisher publisher = publisherDao.findById(sociologicalSurveyDTO.getPublisher().getPublisherId()).orElseThrow(() -> new EntityNotFoundException("Sociological Survey with ID " + sociologicalSurveyDTO.getPublisher().getPublisherId() + " not found!"));
        SociologicalSurvey sociologicalSurvey = sociologicalSurveyMapper.fromSociologicalSurveyDTO(sociologicalSurveyDTO);
        sociologicalSurvey.setPublisher(publisher);
        sociologicalSurvey.setVoters(loadedSociologicalSurvey.getVoters());
        SociologicalSurvey updatedSociologicalSurvey = sociologicalSurveyDao.save(sociologicalSurvey);
        return sociologicalSurveyMapper.fromSociologicalSurvey(updatedSociologicalSurvey);

    }

    @Override
    public Page<SociologicalSurveyDTO> findSociologicalSurveyByTitle(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SociologicalSurvey> sociologicalSurveysPage = sociologicalSurveyDao.findSociologicalSurveyByTitleContains(keyword, pageRequest);
        return new PageImpl<>(sociologicalSurveysPage.getContent().stream().map(sociologicalSurvey -> sociologicalSurveyMapper.fromSociologicalSurvey(sociologicalSurvey)).collect(Collectors.toList()), pageRequest, sociologicalSurveysPage.getTotalElements());
    }

    @Override
    public void assignVoterToSociologicalSurvey(Long sociologicalSurveyId, Long voterId) {
        Voter voter = voterDao.findVoterByVoterId(voterId);
        SociologicalSurvey sociologicalSurvey = loadSociologicalSurveyById(sociologicalSurveyId);
        sociologicalSurvey.assignVoterToSociologicalSurvey(voter);
    }

    @Override
    public Page<SociologicalSurveyDTO> fetchSociologicalSurveysForVoter(Long voterId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SociologicalSurvey> voterSociologicalSurveysPage = sociologicalSurveyDao.findSociologicalSurveysByVoterId(voterId, pageRequest);
        return new PageImpl<>(voterSociologicalSurveysPage.getContent().stream().map(sociologicalSurvey -> sociologicalSurveyMapper.fromSociologicalSurvey(sociologicalSurvey)).collect(Collectors.toList()), pageRequest, voterSociologicalSurveysPage.getTotalElements());
    }

    @Override
    public Page<SociologicalSurveyDTO> fetchSociologicalSurveysForNonSubscribedOnVoter(Long voterId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SociologicalSurvey> voterNonSubscribedOnSociologicalSurveysPage = sociologicalSurveyDao.findNonSubscribedOnSociologicalSurveysByVoterId(voterId, pageRequest);
        return new PageImpl<>(voterNonSubscribedOnSociologicalSurveysPage.getContent().stream().map(sociologicalSurvey -> sociologicalSurveyMapper.fromSociologicalSurvey(sociologicalSurvey)).collect(Collectors.toList()), pageRequest, voterNonSubscribedOnSociologicalSurveysPage.getTotalElements());

    }

    @Override
    public void removeSociologicalSurvey(Long sociologicalSurveyId) {
        fetchQuestionsBySociologicalSurveyId(sociologicalSurveyId).forEach(question -> this.questionDao.deleteById(question.getQuestionId()));
        sociologicalSurveyDao.deleteById(sociologicalSurveyId);
    }

    @Override
    public Page<SociologicalSurveyDTO> fetchSociologicalSurveysForPublisher(Long publisherId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SociologicalSurvey> publishersSociologicalSurveysPage = sociologicalSurveyDao.findSociologicalSurveysByPublisherId(publisherId, pageRequest);
        return new PageImpl<>(publishersSociologicalSurveysPage.getContent().stream().map(sociologicalSurvey -> sociologicalSurveyMapper.fromSociologicalSurvey(sociologicalSurvey)).collect(Collectors.toList()), pageRequest, publishersSociologicalSurveysPage.getTotalElements());

    }

    @Override
    public void addQuestionToSociologicalSurvey(QuestionDTO question, Long sociologicalSurveyId) {
        SociologicalSurvey loadedSociologicalSurvey = sociologicalSurveyDao.findSociologicalSurveyBySociologicalSurveyId(sociologicalSurveyId);
        Question createdQuestion = questionService.createQuestion(question);
        loadedSociologicalSurvey.addQuestionToSociologicalSurvey(createdQuestion);
        updateSociologicalSurvey(sociologicalSurveyMapper.fromSociologicalSurvey(loadedSociologicalSurvey));
    }

    @Override
    public List<QuestionDTO> fetchQuestionsBySociologicalSurveyId(Long sociologicalSurveyId) {
        List<Question> sociologicalSurveyQuestionsPage = sociologicalSurveyDao.findQuestionsBySociologicalSurveyId(sociologicalSurveyId);
        return sociologicalSurveyQuestionsPage.stream().map(question -> sociologicalSurveyMapper.fromQuestion(question)).collect(Collectors.toList());
    }

    @Override
    public void incrementOptionValue(Long sociologicalSurveyId, String optionKey) {
        SociologicalSurvey sociologicalSurvey = loadSociologicalSurveyById(sociologicalSurveyId);

        if (sociologicalSurvey != null && sociologicalSurvey.getQuestions() != null) {
            for (Question question : sociologicalSurvey.getQuestions()) {
                if (question.getOptions() != null && question.getOptions().containsKey(optionKey)) {
                    int currentValue = question.getOptions().get(optionKey);
                    question.getOptions().put(optionKey, currentValue + 1);

                    // Save the updated question back to the database or update it as needed
                    questionDao.save(question);
                }
            }
        }
    }


}
//sociologicalSurvey.getQuestions().forEach(question -> {
////            Map<String, Integer> options = question.getOptions();
////            if (options.containsKey(optionKey)) {
////                int currentValue = options.get(optionKey);
////                options.put(optionKey, currentValue + 1);
////            }
////            questionDao.save(question);
////        });