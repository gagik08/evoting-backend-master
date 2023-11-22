package com.capstone.epam.evotingsystem.service;


import com.capstone.epam.evotingsystem.dto.PublisherDTO;
import com.capstone.epam.evotingsystem.entity.Publisher;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PublisherService {

    Publisher loadPublisherById(Long publisherId);
    Page<PublisherDTO> findPublishersByName(String name, int page, int size);
    PublisherDTO loadPublisherByUsername(String username);
//    PublisherDTO loadPublisherByPublicName(String username);
    PublisherDTO createPublisher(PublisherDTO publisherDTO);
    PublisherDTO updatePublisher(PublisherDTO publisherDTO);
    List<PublisherDTO> fetchPublishers();
    void removePublisher(Long publisherId);

    Page<Object> fetchPublicationsByPublisherId(Long publisherId, int page, int size);
}
