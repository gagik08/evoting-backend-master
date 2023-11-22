package com.capstone.epam.evotingsystem.services;

import com.capstone.epam.evotingsystem.dao.voting.survey.QuestionDao;
import com.capstone.epam.evotingsystem.dto.voting.survey.QuestionDTO;
import com.capstone.epam.evotingsystem.entity.voting.survey.Question;
import com.capstone.epam.evotingsystem.mapper.voting.survey.SociologicalSurveyMapper;
import com.capstone.epam.evotingsystem.service.impl.voting.survey.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private SociologicalSurveyMapper sociologicalSurveyMapper;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    public void testCreateQuestion() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setContent("Sample Question");

        Map<String, Integer> options = new HashMap<>();
        options.put("Option 1", 1);
        options.put("Option 2", 2);
        options.put("Option 3", 3);
        questionDTO.setOptions(options);

        Mockito.when(questionDao.findQuestionByContent(anyString())).thenReturn(null); // Assuming no question with the same content exists
        Mockito.when(sociologicalSurveyMapper.fromQuestionDTO(any())).thenReturn(new Question()); // Mock the mapping behavior
        Mockito.when(questionDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0)); // Return the saved question

        Question createdQuestion = questionService.createQuestion(questionDTO);

        assertNotNull(createdQuestion);
        System.out.println(createdQuestion.getContent());
        assertEquals("Sample Question", createdQuestion.getContent());
        assertEquals(options, createdQuestion.getOptions());

        verify(questionDao, times(1)).save(any());
    }
}
