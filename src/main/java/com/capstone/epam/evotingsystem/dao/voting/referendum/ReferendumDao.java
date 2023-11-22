package com.capstone.epam.evotingsystem.dao.voting.referendum;

import com.capstone.epam.evotingsystem.entity.voting.referendum.Referendum;
import com.capstone.epam.evotingsystem.entity.voting.referendum.ReferendumQuestion;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReferendumDao extends JpaRepository<Referendum, Long> {
    Referendum findReferendumByReferendumId(Long referendumId);

//    @Query("SELECT r FROM Referendum AS r WHERE r.referendumId=:referendumId")
//    Referendum findReferendumByReferendumId(Long referendumId);

    Page<Referendum> findReferendumByTitleContains(String keyword, Pageable pageable);

    @Query("SELECT r FROM Referendum r JOIN r.voters v WHERE v.voterId = :voterId")
    Page<Referendum> findReferendumsByVoterId(@Param("voterId") Long voterId, Pageable pageable);

    @Query(value = "SELECT * FROM referendums AS r WHERE referendum_id NOT IN (SELECT r.referendum_id FROM referendum_voters AS r WHERE r.voter_id=:voterId)", nativeQuery = true)
    Page<Referendum> findNonSubscribedOnReferendumsByVoterId(@Param("voterId") Long voterId, Pageable pageable);

    @Query(value = "SELECT r FROM Referendum AS r WHERE r.publisher.publisherId=:publisherId")
    Page<Referendum> findReferendumsByPublisherId(@Param("publisherId") Long publisherId, Pageable pageable);

    Referendum findReferendumByDescription(String description);
    Referendum findReferendumByTitle(String title);

    @Query("SELECT r.referendumQuestion FROM Referendum r WHERE r.referendumId = :referendumId")
    ReferendumQuestion findReferendumQuestionByReferendumId(@Param("referendumId") Long referendumId);

}
