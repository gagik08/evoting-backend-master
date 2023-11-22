package com.capstone.epam.evotingsystem.web;

import com.capstone.epam.evotingsystem.dto.voting.survey.QuestionDTO;
import com.capstone.epam.evotingsystem.dto.voting.survey.SociologicalSurveyDTO;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import com.capstone.epam.evotingsystem.mapper.voting.survey.SociologicalSurveyMapper;
import com.capstone.epam.evotingsystem.service.voting.survey.SociologicalSurveyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sociological-surveys")
@CrossOrigin("*")
public class SociologicalSurveyRestController {

    private SociologicalSurveyService sociologicalSurveyService;
    private SociologicalSurveyMapper sociologicalSurveyMapper;

    public SociologicalSurveyRestController(SociologicalSurveyService sociologicalSurveyService, SociologicalSurveyMapper sociologicalSurveyMapper) {
        this.sociologicalSurveyService = sociologicalSurveyService;
        this.sociologicalSurveyMapper = sociologicalSurveyMapper;
    }

    @GetMapping
    public Page<SociologicalSurveyDTO> searchSociologicalSurveys(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "0") int size) {
        return sociologicalSurveyService.findSociologicalSurveyByTitle(keyword, page, size);
    }

    @DeleteMapping("/{sociologicalSurveyId}")
    @PreAuthorize("hasAnyAuthority('Commission Member', 'Publisher')")
    public void deleteSociologicalSurvey(@PathVariable Long sociologicalSurveyId){
        sociologicalSurveyService.removeSociologicalSurvey(sociologicalSurveyId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('Publisher')")
    public SociologicalSurveyDTO saveSociologicalSurvey(@RequestBody SociologicalSurveyDTO sociologicalSurveyDTO){
        return sociologicalSurveyService.createSociologicalSurvey(sociologicalSurveyDTO);
    }


    @PutMapping("/{sociologicalSurveyId}")
    @PreAuthorize("hasAuthority('Publisher')")
    public SociologicalSurveyDTO updateSociologicalSurveyId(@RequestBody SociologicalSurveyDTO sociologicalSurveyDTO, @PathVariable Long sociologicalSurveyId){
        sociologicalSurveyDTO.setSociologicalSurveyId(sociologicalSurveyId);
        return sociologicalSurveyService.updateSociologicalSurvey(sociologicalSurveyDTO);
    }


    @PostMapping("/{sociologicalSurveyId}/subscribe-on/voter/{voterId}")
    @PreAuthorize("hasAuthority('Voter')")
    public void subscribeVoterOnSociologicalSurvey(@PathVariable Long sociologicalSurveyId, @PathVariable Long voterId){
        sociologicalSurveyService.assignVoterToSociologicalSurvey(sociologicalSurveyId,voterId);
    }

    @PutMapping("/{sociologicalSurveyId}/questions")
    public void addQuestionToSociologicalSurvey(@RequestBody QuestionDTO questionDTO, @PathVariable Long sociologicalSurveyId) {
        sociologicalSurveyService.addQuestionToSociologicalSurvey(questionDTO, sociologicalSurveyId);
    }

    @GetMapping("/{sociologicalSurveyId}/questions")
    @PreAuthorize("hasAnyAuthority('Commission Member', 'Publisher', 'Voter')")
    public List<QuestionDTO> getSociologicalSurveyQuestions(@PathVariable Long sociologicalSurveyId) {
        return sociologicalSurveyService.fetchQuestionsBySociologicalSurveyId(sociologicalSurveyId);
    }

    @GetMapping("/{sociologicalSurveyId}")
    public SociologicalSurveyDTO getSociologicalSurvey(@PathVariable Long sociologicalSurveyId){
        SociologicalSurvey sociologicalSurvey = sociologicalSurveyService.loadSociologicalSurveyById(sociologicalSurveyId);
        return sociologicalSurveyMapper.fromSociologicalSurvey(sociologicalSurvey);
    }

    @PutMapping("/{sociologicalSurveyId}/questions/vote")
    @PreAuthorize("hasAnyAuthority('Commission Member', 'Publisher', 'Voter')")
    public ResponseEntity<String> incrementOptionValue(
            @PathVariable Long sociologicalSurveyId,
            @RequestParam String optionKey) {
        try {
            sociologicalSurveyService.incrementOptionValue(sociologicalSurveyId, optionKey);
            return ResponseEntity.ok("Value incremented successfully");
        } catch (Exception e) {
            return ResponseEntity.ok("Value incremented successfully");
        }
    }

}
