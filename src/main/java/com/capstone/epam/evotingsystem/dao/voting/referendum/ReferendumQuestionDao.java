package com.capstone.epam.evotingsystem.dao.voting.referendum;

import com.capstone.epam.evotingsystem.entity.voting.referendum.ReferendumQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferendumQuestionDao extends JpaRepository<ReferendumQuestion, Long> {


}
