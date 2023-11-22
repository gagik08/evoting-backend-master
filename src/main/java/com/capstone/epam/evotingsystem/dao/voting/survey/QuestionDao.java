package com.capstone.epam.evotingsystem.dao.voting.survey;

import com.capstone.epam.evotingsystem.entity.voting.survey.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionDao extends JpaRepository<Question, Long> {
    Question findQuestionByContent(String content);

    Question findQuestionByQuestionId(Long questionId);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM questions WHERE id = :id")
    void deleteQuestionById(@Param("id") Long id);
}
