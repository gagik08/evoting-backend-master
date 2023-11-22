package com.capstone.epam.evotingsystem.dao;

import com.capstone.epam.evotingsystem.entity.Voter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoterDao extends JpaRepository<Voter, Long> {
    
    @Query("SELECT v FROM Voter v WHERE v.fullName like %:name")
    Voter findVoterByFullName(String name);

    @Query("SELECT v FROM Voter v WHERE v.fullName like %:name")
    Page<Voter> findVotersByName(@Param("name") String name, PageRequest pageRequest);

    @Query("SELECT p FROM Voter p INNER JOIN p.user u WHERE u.username = :username")
    Voter findVoterByUsername(@Param("username") String username);


    Voter findVoterByVoterId(Long sociologicalSurveyId);
}
