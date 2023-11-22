package com.capstone.epam.evotingsystem.dao.voting;

import com.capstone.epam.evotingsystem.entity.voting.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Category findCategoryByCategoryTitle(String title);
    Category findCategoryByCategoryId(Long categoryId);

}
//    @Query("SELECT c FROM Category c INNER JOIN c. u WHERE u.firstName like %:name% or u.lastName like %:name%")
//    List<SociologicalSurvey> findCategoryByVotingTitleOrCategoryDescription(@Param("keyword") String keyword);

