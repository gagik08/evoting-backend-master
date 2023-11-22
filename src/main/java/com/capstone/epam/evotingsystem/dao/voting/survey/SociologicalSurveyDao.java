package com.capstone.epam.evotingsystem.dao.voting.survey;

import com.capstone.epam.evotingsystem.entity.voting.survey.Question;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SociologicalSurveyDao extends JpaRepository<SociologicalSurvey, Long> {

    Page<SociologicalSurvey> findSociologicalSurveyByTitleContains(String keyword, Pageable pageable);

    @Query("SELECT s FROM SociologicalSurvey s JOIN s.voters v WHERE v.voterId = :voterId")
    Page<SociologicalSurvey> findSociologicalSurveysByVoterId(@Param("voterId") Long voterId, Pageable pageable);

    @Query(value = "SELECT * FROM sociological_surveys AS s WHERE sociological_survey_id NOT IN (SELECT e.sociological_survey_id FROM sociological_survey_voters AS e WHERE e.voter_id=:voterId)", nativeQuery = true)
    Page<SociologicalSurvey> findNonSubscribedOnSociologicalSurveysByVoterId(@Param("voterId") Long voterId, Pageable pageable);

    @Query(value = "SELECT s FROM SociologicalSurvey AS s WHERE s.publisher.publisherId=:publisherId")
    Page<SociologicalSurvey> findSociologicalSurveysByPublisherId(@Param("publisherId") Long publisherId, Pageable pageable);

//    @Query("SELECT s.questions FROM SociologicalSurvey s WHERE s.sociologicalSurveyId = :sociologicalSurveyId")
//    List<Question> findQuestionsBySociologicalSurveyId(@Param("sociologicalSurveyId") Long sociologicalSurveyId);

    SociologicalSurvey findSociologicalSurveyBySociologicalSurveyId(Long sociologicalSurveyId);
    @Query("SELECT s.questions FROM SociologicalSurvey s WHERE s.sociologicalSurveyId = :sociologicalSurveyId")
    List<Question> findQuestionsBySociologicalSurveyId(@Param("sociologicalSurveyId") Long sociologicalSurveyId);
}
