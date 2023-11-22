package com.capstone.epam.evotingsystem.dao;

import com.capstone.epam.evotingsystem.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PublisherDao extends JpaRepository<Publisher, Long> {

    Publisher findPublisherByPublicName(String publicName);

    @Query("SELECT p FROM Publisher p WHERE p.publicName like %:name% ")
    Page<Publisher> findPublishersByName(@Param("name") String name, PageRequest pageRequest);

    @Query(value = "SELECT p FROM Publisher p INNER JOIN p.user u WHERE u.username = :username")
    Publisher findPublisherByUsername(@Param("username") String username);


}
